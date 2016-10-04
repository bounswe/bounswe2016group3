package bounswegroup3.mail;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;

public class Mailer {
	private MailjetClient client;
	private String senderName;
	private String senderMail;
	
	public Mailer(String key, String secret, String sendeName, String senderMail){
		client = new MailjetClient(key, secret);
		this.senderName = senderName;
		this.senderMail = senderMail;
	}
	
	public void sendMail(String recipient, String title, String body){
		MailjetRequest request = new MailjetRequest(Email.resource)
				.property(Email.FROMEMAIL, senderMail)
				.property(Email.FROMNAME, senderName)
				.property(Email.SUBJECT, title)
				.property(Email.HTMLPART, body)
				.property(Email.RECIPIENTS, new JSONArray()
						.put(new JSONObject().put("Email", recipient)));
		try {
			client.post(request);
		} catch (MailjetException e) {
			System.out.println("Mailing error");
			e.printStackTrace();
		}
	}
}
