package com.ifeng.ad.mutacenter.common.bean.mysqlmap;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.ifeng.ad.mutacenter.common.utils.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 投放清单
 */
public class ShowTime {
    public Long posId;
    public String cpd; //逗号分隔
    public String wings; //凤翼的配置id
    public String allowance; //逗号分隔
    public String status;

//    public static ShowTime creater(String posId) {
//        ShowTime showTime = new ShowTime();
//        showTime.setPosId(posId);
//        return showTime;
//    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public String getCpd() {
        return cpd;
    }

    /**
     * cpd接收的数据为 订单id_轮播数 例如 31_2,11_1
     * 拆分为具体的个数，例如上面例子返回为 31,31,11,11
     * @param cpd
     */
    public void setCpd(String cpd) {
        if(StringUtils.isNotEmpty(cpd)) {
            List<String> list = Splitter.on(",").splitToList(cpd);
            List<String> finallist = new LinkedList<>();
            if(list != null) {
                list.stream()
                        .filter(StringUtils::isNotEmpty)
                        .forEach(v -> {
                            String[] vs = v.split("_");

                            if(vs.length == 1) {
                                finallist.add(vs[0]);
                            }
                            else {
                                Integer num = Integer.parseInt(vs[1]);
                                for(int i = 0; i < num; i++) {
                                    finallist.add(vs[0]);
                                }
                            }
                        });
            }

            if(finallist.size() > 0) {
                this.cpd = Joiner.on(",").join(finallist);
            }
        }
    }

    public String getWings() {
        return wings;
    }

    public void setWings(String wings) {
        this.wings = wings;
    }

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
