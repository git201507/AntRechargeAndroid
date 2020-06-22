package com.ant.recharge.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
/**
 * Created by xiaowei.wang on 2016/4/26.
 * 后续如果有新增字段，开发者只需要在此类中加上即可
 */
public class Charge implements Serializable {
    //支付订单id，系统内唯一，以“ch_”开头，后跟24位随机数
    private String id;
    //商户订单号，在商户系统内唯一，8-20位数字或字母，不允许特殊字符
    private String orderNo;
    //订单总金额，大于0的数字，单位是该币种的货币单位
    private BigDecimal amount;
    //购买商品的标题，最长32位
    private String subject;
    //购买商品的描述信息，最长128个字符
    private String body;
    //支付渠道编码，唯一标识一个支付渠道
    private String channel;
    //发起支付的客户端IP
    private String clientIp;
    //	订单备注，限制300个字符内
    private String description;
    //	用户自定义元数据
    private Map<String, Object> metadata;
    //特定渠道需要的的额外附加参数
    private Map<String, Object> extra;
    //应用的appKey
    private String app;
    //已结算金额
    private BigDecimal amountSettle;
    //三位ISO货币代码，只支持人民币cny，默认cny
    private String currency;
    //已退款金额
    private BigDecimal amountRefunded;
    //订单创建时间，13位时间戳
    private Long timeCreated;
    //	订单失效时间，13位时间戳
    private Long timeExpire;
    //支付凭据，用于调起支付APP或者跳转支付网关
    private Map<String, Object> credential;
    //是否是生产模式
    private Boolean liveMode;
    //订单状态，只有三种（PROCESSING-处理中，SUCCEED-成功，FAILED-失败）
    private String status;
    //本次请求是否成功 true:成功,false:失败
    private Boolean reqSuccessFlag;

    public Boolean getReqSuccessFlag() {
        return reqSuccessFlag;
    }

    public void setReqSuccessFlag(Boolean reqSuccessFlag) {
        this.reqSuccessFlag = reqSuccessFlag;
    }
    public String getOrderNo() {
        return orderNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }


    public BigDecimal getAmountSettle() {
        return amountSettle;
    }

    public void setAmountSettle(BigDecimal amountSettle) {
        this.amountSettle = amountSettle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(BigDecimal amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public Long getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Long timeExpire) {
        this.timeExpire = timeExpire;
    }

    public Map<String, Object> getCredential() {
        return credential;
    }

    public void setCredential(Map<String, Object> credential) {
        this.credential = credential;
    }

    public Boolean getLiveMode() {
        return liveMode;
    }

    public void setLiveMode(Boolean liveMode) {
        this.liveMode = liveMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
