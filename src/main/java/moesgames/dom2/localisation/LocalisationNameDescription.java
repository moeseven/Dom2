package moesgames.dom2.localisation;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;


public class LocalisationNameDescription implements Serializable{
	
	public LocalisationNameDescription addLanguage(Language l,String name) {
		return addLanguage(l, name, name);
	}
	
	public static String insertSpaceAfterEveryChar(String input) {
	    if (input == null || input.isEmpty()) {
	        return input;
	    }

	    BreakIterator graphemeIterator = BreakIterator.getCharacterInstance();
	    graphemeIterator.setText(input);

	    StringBuilder sb = new StringBuilder();
	    int start = graphemeIterator.first();
	    int end = graphemeIterator.next();

	    while (end != BreakIterator.DONE) {
	        String grapheme = input.substring(start, end);
	        sb.append(grapheme);

	        // Check if we should add a space: not after the last grapheme,
	        // and optionally skip if the grapheme itself is '§' (adjust as needed)
	        if (end != input.length() && !replaceSymbolKeywords.equals(grapheme)) {
	            sb.append(' ');
	        }

	        start = end;
	        end = graphemeIterator.next();
	    }

	    return sb.toString();
	}
	
	public LocalisationNameDescription addLanguage(Language l,String name, String description) {
		if (!getName(Language.english).equals("")) {
			assert(!nameMap.containsKey(l));
		}
		if (l == Language.chinese) {
			description = insertSpaceAfterEveryChar(description);
		}
		if (keywords != null) {
			description = replaceKeyword(description,l);
			name = replaceKeyword(name, l);
		}
		textMap.put(l, description);
		nameMap.put(l, name);
		return this;
	}
	
	private String replaceKeyword(String description,Language l) {
		for (int i = keywords.length-1; i >=0 ; i--) {
			String replaceString = replaceSymbolKeywords;
			if (i>0) {
				replaceString+=i;
			}
			description = description.replace(replaceString,addKeywordHighlighting(keywords[i].getName(l)));
		}
		return description;
	}

	public StringBuilder addKeywordHighlighting(String name) {
		StringBuilder sb = new StringBuilder("{"+name.replace(" ", "} {"));
		sb.append("}");
		return sb;
	}

	LocalisationNameDescription[] keywords = null; static final String replaceSymbolKeywords = "§";
	
	public Map<Language, String> getTextMap() {
		return textMap;
	}

	public Map<Language, String> getNameMap() {
		return nameMap;
	}


	private Map<Language,String> textMap = new HashMap<Language, String>();
	private Map<Language,String> nameMap = new HashMap<Language, String>();
	
	
	private String fromMap(Map<Language,String> map, Language language, boolean trimSpecial) {
		String retVal;
		if (map.containsKey(language)) {
			retVal =  map.get(language);
		}else {
			retVal = map.get(Language.english);
		}		
		if (trimSpecial) {
			retVal = retVal.replace("{", "").replace("}", "");
		}
		return retVal;
	}
	
	public String getPlainName(Language language) {
		return fromMap(nameMap, language, true);
	}
	
	public String getName(Language language) {
		return fromMap(nameMap, language, false);
	}

	public void setName(String text,Language language) {
		nameMap.put(language, text);
	}

	public String getPlainText(Language language) {
		return fromMap(textMap, language, true);
	}
	
	public String getText(Language language) {
		return fromMap(textMap, language, false);
	}
	

	public void setText(String text,Language language) {
		textMap.put(language, text);
	}

	public LocalisationNameDescription(String name,String text) {
		textMap.put(Language.english, text);
		nameMap.put(Language.english, name);
	}
	
	public LocalisationNameDescription(LocalisationNameDescription keyword) {
		this();
		this.keywords = new LocalisationNameDescription[]{keyword};
	}
	
	public LocalisationNameDescription(LocalisationNameDescription[] keywords) {
		this();
		this.keywords = keywords;
	}
	
	public LocalisationNameDescription() {
		this("","");
	}
	
	
	

}
