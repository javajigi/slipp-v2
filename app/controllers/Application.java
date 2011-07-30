package controllers;

import play.modules.gae.GAE;
import play.mvc.Controller;
import play.mvc.With;
import supports.web.Check;
import supports.web.GAESecure;
import supports.web.Role;

@With(GAESecure.class)
public class Application extends Controller {
    public static void keepalived() {
    	render();
    }
    
    public static void about() {
    	render();
    }
    
    public static void code() {
    	render();
    }

    @Check(Role.ROLE_USER)
    public static void login() {
    	Threads.list(1);
    }
 
    public static void logout() {
    	GAE.logout("Threads.list");
    }
}