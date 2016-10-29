package bounswegroup3.client;

import com.codahale.metrics.health.HealthCheck;

public class ClientHealthCheck extends HealthCheck {
	private ServiceClient client;
	private String serviceName;
	
	public ClientHealthCheck(String serviceName, ServiceClient client) {
		this.client = client;
		this.serviceName = serviceName;
	}
	
	@Override
	protected Result check() throws Exception {
		if(client.checkValidity()) {
			return Result.healthy();
		} else {
			return Result.unhealthy("Can't connect to " + serviceName);
		}
	}

}
