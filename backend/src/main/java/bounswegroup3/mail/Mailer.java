package bounswegroup3.mail;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Apikeytotals;
import com.mailjet.client.resource.Email;

import bounswegroup3.client.ServiceClient;

/**
 * Facilitates communication with the Mailjet API to
 * be able to send emails
 */
public class Mailer implements ServiceClient {
	private MailjetClient client;
	private String senderName;
	private String senderMail;
	
	/**
	 * Initializes the client with Mailjet credentials and sender info
	 * @param key Your Mailjet API Key
	 * @param secret Obtained from Mailjet API 
	 * @param senderName The name of the mail's sender
	 * @param senderMail The email address of the mail's sender
	 */
	public Mailer(String key, String secret, String senderName, String senderMail){
		client = new MailjetClient(key, secret);
		this.senderName = senderName;
		this.senderMail = senderMail;
	}
	
	/**
	 * Send an arbitrary email 
	 * @param recipient The email address of the recipient
	 * @param title Title of the mail
	 * @param body Body of the mail. Can be either plaintext or HTML, but the mail is sent 
	 * as an HTML document.
	 */
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

	@Override
	public boolean checkValidity() {
		MailjetResponse res;
		try {
			res = client.get(new MailjetRequest(Apikeytotals.resource));

			return res.getStatus() == 200;
		} catch (MailjetException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
