# Mutalisk

spring boot + Vert.x

use mysharkdb;
create table jijin(id int auto_increment primary key,
jzrq varchar(20) comment '净值日期',
fundcode varchar(10) comment '基金代码',
dwjz float comment '单位净值',
gsz float comment '估计值',
gszzl float comment '估计涨幅',
gztime datetime comment '估计时间',
d20jz float comment '20天均值',
grz float comment '过热值',
glz float comment '过冷值'
);

drop table jijin_info;
create table jijin_info(id int auto_increment primary key,
fundcode varchar(20) comment '基金代码',
`name` varchar(100) comment '基金名称',
`status` int comment '是否启用 0 启用',
gzzs varchar(20) comment '跟踪的指数',
unique index fundcode_index(fundcode)
) default charset = utf8;




select * from jijin where fundcode = '007475' order by id desc ;

select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b
on a.fundcode = b.fundcode where a.jzrq =date_format(now(), '%Y-%m-%d')
and a.fundcode ='007475'
order by fundcode ,jzrq desc ;
-- 查询当日情况
select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b
on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d');
-- 查询过热的基金
select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b
on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d') and dwjz>grz;

-- 查询过热的基金 上涨2%过热
select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b
on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d') and dwjz*1.01>grz and dwjz < grz;

-- 查询过冷基金
select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b
on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d') and dwjz<glz;

-- 查询过冷基金 下跌2%过冷
select jzrq,a.fundcode,`name`,dwjz,d20jz,grz,glz from jijin a inner join jijin_info b
on a.fundcode = b.fundcode where jzrq  =date_format(now(), '%Y-%m-%d') and dwjz>glz and dwjz*0.99<glz;


