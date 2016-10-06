package bounswegroup3.mail;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

public class Template {
	private ST tpl;
	public Template(String filename){
		tpl = new STGroupDir("templates").getInstanceOf(filename);
	}
	
	public ST add(String k, String v){
		return tpl.add(k,v);
	}
	
	public String render(){
		return tpl.render();
	}
}
