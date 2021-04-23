package cats;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalKeyListener implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        /*if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
        }*/
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        //System.out.println("Key Typed: " + key);
        if (key.equals("Back Quote")) {
        	String fact = "";
        	if (CatFacts.factType == 0)
        		fact = getCatFact();
        	else if (CatFacts.factType == 1)
        		fact = getPandaFact();
        	else if (CatFacts.factType == 2)
        		fact = getDonaldTrump();
        	else if (CatFacts.factType == 3)
        		fact = getDadJoke();
        	copyToClipboard(fact);
        	if (CatFacts.soundOn) {
        		if (!fact.equals(""))
                	if (CatFacts.factList.size() > 100)
                		CatFacts.sm.playSound("Meow sound effect.wav");
                	else
                		CatFacts.sm.playSound("Ding Sound Effect.wav");
        		else
        			CatFacts.sm.playSound("FAIL SOUND EFFECT.wav");
        	}
        } /*else if (key.equals("Scroll Lock")) {
        	System.exit(0);
        } else if (key.equals("F1")) {
        	CatFacts.factType++;
        	if (CatFacts.factType > 4)
        		CatFacts.factType = 0;
        }*/ else if (key.equals("F2")) {
        	CatFacts.factList.clear();
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
    
    public void copyToClipboard(String text) {
    	StringSelection stringSelection = new StringSelection(text);
    	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    	clipboard.setContents(stringSelection, null);
    }
    
    public String getCatFact() {
    	String pageName = "https://catfact.ninja/fact";
    	try {
        	JSONObject json = JsonReader.readJsonFromUrl(pageName);
        	String fact = (String) json.get("fact");
        	int length = (int) json.get("length");
        	long curr = System.currentTimeMillis();
        	long delay = 0;
        	//System.out.println("length 1: " + length);
        	while (length > 195 || CatFacts.factList.contains(fact)) {
        		json = JsonReader.readJsonFromUrl(pageName);
        		fact = (String) json.get("fact");
        		length = (int) json.get("length");
        		delay = System.currentTimeMillis() - curr;
        		//System.out.println("length 2: " + length);
        	}
        	if (7000 < delay)
        		fact = "";
        	if (!CatFacts.factList.contains(fact) && !fact.equals(""))
        		CatFacts.factList.add(fact);
        	//System.out.println(fact);
        	return fact;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    public String getDonaldTrump() {
    	String pageName = "https://api.whatdoestrumpthink.com/api/v1/quotes/random";
    	try {
        	JSONObject json = JsonReader.readJsonFromUrl(pageName);
        	String fact = ((String) json.get("message")) + " - Donald Trump";
        	return fact;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
	public String getDadJoke() {
		String pageName = "https://icanhazdadjoke.com";
		try {
			JSONObject json = JsonReader.readJsonFromUrl(pageName, "application/json");
			String fact = ((String) json.get("joke"));
			return fact;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getPandaFact() {
		String pageName = "https://some-random-api.ml/facts/panda";
    	try {
        	JSONObject json = JsonReader.readJsonFromUrl(pageName);
        	String fact = (String) json.get("fact");
        	return fact;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
    }  
}
