package controllers;

import java.util.List;

import models.Auth;

import play.mvc.Controller;
import play.mvc.With;
import supports.web.GAESecure;

@With(GAESecure.class)
public class Application extends Controller {
    public static void keepalived() {
    	render();
    }
    
    public static void about() {
    	render();
    }

    public static void login() {
        Auth.login("Threads.list");
    }
 
    public static void logout() {
        Auth.logout("Threads.list");
    }
}