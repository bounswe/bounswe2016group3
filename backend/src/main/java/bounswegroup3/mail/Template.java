package bounswegroup3.mail;

import org.stringtemplate.v4.ST;

public class Template {
	private ST tpl;
	
	public Template(String filename){
		tpl = new ST(getClass().getClassLoader().getResource("templates"+filename).getFile());
	}
	
	public ST add(String k, String v){
		return tpl.add(k,v);
	}
	
	public String render(){
		return tpl.render();
	}
}
