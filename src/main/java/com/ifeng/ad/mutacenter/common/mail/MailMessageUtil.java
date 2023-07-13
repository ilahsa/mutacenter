package com.ifeng.ad.mutacenter.common.mail;

import com.ifeng.ad.mutacenter.common.utils.StringUtils;
import com.ifeng.ad.mutacenter.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class MailMessageUtil {
    private static Logger logger = LoggerFactory.getLogger(MailMessageUtil.class);
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private RedisService redisService;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.to}")
    private String to;

    @Value("${spring.mail.warntime}")
    private int warntime;

    private static Map<String, MailStatus> localPunishMap = new HashMap<>();

    public void send(String msg) {
        send("凤飞RTB惩罚恢复通知",msg);
    }
    public void send(String subject,String msg) {
        System.out.println("===========>" + msg);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setText(msg, true);
            helper.setFrom(from);
            if(to != null) {
                helper.setTo(to.split(";"));
            }
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    public void send(String subject,String msg,String toUsers) {
        System.out.println("===========>" + msg);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setText(msg, true);
            helper.setFrom(from);
            if(toUsers != null) {
                helper.setTo(toUsers.split(";"));
            }
            mailSender.send(message);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }
    //收集报警日志，进行邮件发送操作
    public void collectionDsppunish() {
        Map<String, String> redisPunishMap = redisService.hgetAll("dsppunish");

        if(redisPunishMap != null && redisPunishMap.size() > 0) {
            for(Map.Entry<String, String> redisPunish : redisPunishMap.entrySet()) {
                String ip_url = redisPunish.getKey();
                String time = redisPunish.getValue();

                try {
                    //先获取本地数据进行对比
                    MailStatus mailStatus = localPunishMap.get(ip_url);
                    LocalDateTime redisDateTime = LocalDateTime.parse(time);

                    if(mailStatus == null) {
                        String serverip = ip_url.substring(0, ip_url.indexOf("|"));
                        String url = ip_url.substring(ip_url.indexOf("|")+1);
                        mailStatus = new MailStatus();
                        mailStatus.setDspUrl(url);
                        mailStatus.setFfServerIp(serverip);
                        mailStatus.setLastCatchTime(redisDateTime);
                        mailStatus.setFirstWarnTime(redisDateTime);
                        mailStatus.setWarnCount(1);
                        mailStatus.setWarnMail(false);
                        localPunishMap.put(ip_url, mailStatus);
                    }
                    else if(!redisDateTime.equals(mailStatus.getLastCatchTime())) {
                        //不相等则有可能发邮件
                        LocalDateTime lastCatchTime = mailStatus.getLastCatchTime();

                        long time1 = getLocalDateTimeOfThisTenMinute(redisDateTime).toEpochSecond(ZoneOffset.of("+8")); //redis的获取到的时间
                        long time2 = getLocalDateTimeOfThisTenMinute(lastCatchTime).toEpochSecond(ZoneOffset.of("+8")); //上一次运行时间

                        //换算以后的时间，间隔10分钟，则为连续错误
                        if((time1 - time2) == 10*60L) {
                            mailStatus.setLastCatchTime(redisDateTime);
                            mailStatus.incrWarnCount(1);
                        }
                        else {
                            mailStatus.setLastCatchTime(redisDateTime);
                            mailStatus.setFirstWarnTime(redisDateTime);
                            mailStatus.setWarnCount(1);
                        }
                    }
                }
                catch (Exception e) {
                    logger.error("解析redis中的dsp报警信息出现错误, key:" + ip_url
                            + " value:" + time , e);
                    //如果在解析的时候出现异常，则跳过这一条数据
                    continue;
                }
            }
        }

        //整理mail的发送格式，key发送或者恢复请求的标题，value记录地址的列表
        Map<String, List<String>> mailMap = new HashMap<>();

        localPunishMap.values().stream().forEach(mailStatus -> {
            String mailKey = null;

            if(mailStatus.getWarnCount() >= warntime && !mailStatus.isWarnMail()) {
                //报警邮件
                mailStatus.setWarnMail(true);
                mailKey = "muta投放机(" + mailStatus.getFfServerIp() + ") 于 " +
                        mailStatus.getFirstWarnTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                        " <span style=\"color:red\">停止发送RTB请求</span>";
            }

            if(mailStatus.isWarnMail()) {
                //已经发过报警邮件，随时准备发恢复邮件
                long time1 = getLocalDateTimeOfThisTenMinute(LocalDateTime.now()).toEpochSecond(ZoneOffset.of("+8"));
                long time2 = getLocalDateTimeOfThisTenMinute(mailStatus.getLastCatchTime()).toEpochSecond(ZoneOffset.of("+8"));

                // time1为当前时间所在十分钟的0分钟, time2上一次报警时间所在十分钟的第0分钟
                //我们的逻辑是，在发报警后的第二个十分钟过完以后，还没有
                if((time1 - time2) >= 20*60L) {
                    mailStatus.setWarnMail(false);
                    mailStatus.setWarnCount(0);
                    //恢复邮件
                    mailKey = "muta投放机(" + mailStatus.getFfServerIp() + ") 于 " +
                            getLocalDateTimeOfThisTenMinute(mailStatus.getLastCatchTime()).plusMinutes(10).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                            " <span style=\"color:green\">恢复发送RTB请求</span>";
                }
            }

            if (StringUtils.isNotEmpty(mailKey)) {
                if(!mailMap.containsKey(mailKey) || mailMap.get(mailKey) == null) {
                    List<String> urllist = new LinkedList<>();
                    mailMap.put(mailKey, urllist);
                }

                mailMap.get(mailKey).add(mailStatus.getDspUrl());
            }
        });

        if(mailMap != null && mailMap.size() > 0){
            StringBuilder mailsb = new StringBuilder();

            mailMap.forEach((k, list) -> {
                mailsb.append("<b>").append(k).append("</b><br>");

                for(int i = 0; i < list.size(); i++) {
                    mailsb.append(i+1).append("、").append(list.get(i)).append("<br>");
                }

                mailsb.append("<br>");
            });

            send(mailsb.toString());
        }
    }

    //获取当前时间所在的10分钟的第0分钟,例如2019-07-15T11:37:52.992，得到的结果是2019-07-15T11:30:00.000
    private LocalDateTime getLocalDateTimeOfThisTenMinute(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.getYear(),
                localDateTime.getMonth(),
                localDateTime.getDayOfMonth(),
                localDateTime.getHour(),
                localDateTime.getMinute()/10 * 10, 0, 0);
    }
}
