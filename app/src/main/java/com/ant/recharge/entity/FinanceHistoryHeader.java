package com.ant.recharge.entity;

import java.math.BigDecimal;

/**
 * 理财记录
 * Created by kwc on 2016/9/8.
 */
public class FinanceHistoryHeader {


    private BigDecimal financing;//充值
    private BigDecimal finsh;//提现
    private BigDecimal investment;//理财收入（元）

    public BigDecimal getFinancing() {
        return financing;
    }

    public void setFinancing(BigDecimal financing) {
        this.financing = financing;
    }

    public BigDecimal getFinsh() {
        return finsh;
    }

    public void setFinsh(BigDecimal finsh) {
        this.finsh = finsh;
    }

    public BigDecimal getInvestment() {
        return investment;
    }

    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }
}
