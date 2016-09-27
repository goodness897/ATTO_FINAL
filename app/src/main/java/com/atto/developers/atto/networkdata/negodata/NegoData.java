package com.atto.developers.atto.networkdata.negodata;

import java.util.Arrays;

public class NegoData implements java.io.Serializable {
	private static final long serialVersionUID = -2572726441148153839L;
	int code;
	private int negotiation_id;
	private int trade_id;
	private NegoDataMaker_info maker_info;
	private int negotiation_price;
	private String negotiation_dtime;
	private String negotiation_ctime;
	private String negotiation_product_contents;
	private String[] negotiation_product_imges_info;// null 가능

	public int getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(int trade_id) {
		this.trade_id = trade_id;
	}

	public String getNegotiation_ctime() {
		return negotiation_ctime;
	}

	public void setNegotiation_ctime(String negotiation_ctime) {
		this.negotiation_ctime = negotiation_ctime;
	}

	public String getNegotiation_product_contents() {
		return negotiation_product_contents;
	}

	public void setNegotiation_product_contents(String negotiation_product_contents) {
		this.negotiation_product_contents = negotiation_product_contents;
	}

	public int getNegotiation_id() {
		return this.negotiation_id;
	}

	public void setNegotiation_id(int negotiation_id) {
		this.negotiation_id = negotiation_id;
	}

	public NegoDataMaker_info getMaker_info() {
		return this.maker_info;
	}

	public void setMaker_info(NegoDataMaker_info maker_info) {
		this.maker_info = maker_info;
	}

	public int getNegotiation_price() {
		return this.negotiation_price;
	}

	public void setNegotiation_price(int negotiation_price) {
		this.negotiation_price = negotiation_price;
	}

	public String getNegotiation_dtime() {
		return this.negotiation_dtime;
	}

	public void setNegotiation_dtime(String negotiation_dtime) {
		this.negotiation_dtime = negotiation_dtime;
	}

	public String[] getNegotiation_product_imges_info() {
		return negotiation_product_imges_info;
	}

	public void setNegotiation_product_imges_info(String[] negotiation_product_imges_info) {
		this.negotiation_product_imges_info = negotiation_product_imges_info;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "NegoData{" +
				"code=" + code +
				", negotiation_id=" + negotiation_id +
				", maker_info=" + maker_info +
				", negotiation_price=" + negotiation_price +
				", negotiation_dtime='" + negotiation_dtime + '\'' +
				", negotiation_product_imges_info=" + Arrays.toString(negotiation_product_imges_info) +
				'}';
	}
}
