package SuperStrong.coinWallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinWalletApplication {

	public static void main(String[] args) {
		System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase","true");
		SpringApplication.run(CoinWalletApplication.class, args);
	}

}
