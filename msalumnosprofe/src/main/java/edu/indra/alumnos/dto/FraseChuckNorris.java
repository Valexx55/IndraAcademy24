package edu.indra.alumnos.dto;

/*
 * {
    "categories": [],
    "created_at": "2020-01-05 13:42:23.240175",
    "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id": "x1p5lGJGRryWIxrr5Hx0Wg",
    "updated_at": "2020-01-05 13:42:23.240175",
    "url": "https://api.chucknorris.io/jokes/x1p5lGJGRryWIxrr5Hx0Wg", //HAL/HATEOAS
    "value": "Chuck Norris is on first-name terms with every coroner in Texas. He also has killed many of them."
}
 */

public class FraseChuckNorris {
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public FraseChuckNorris() {
		// TODO Auto-generated constructor stub
	}

	public FraseChuckNorris(String value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "FraseChuckNorris [value=" + value + "]";
	}
	
	
	

}
