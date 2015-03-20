package com.ejie.uda;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class UDAParserErrorHandler implements ErrorHandler {
	
	//Singleton
	private static UDAParserErrorHandler _instance;
    private UDAParserErrorHandler() {  }
    public static synchronized UDAParserErrorHandler getInstance() {
		if (null == _instance) {
		        _instance = new UDAParserErrorHandler();
		}
		return _instance;
    }
	
	@Override
	public void warning(CSSParseException cssParseException) throws CSSException {
	}

	@Override
	public void fatalError(CSSParseException cssParseException) throws CSSException {
		cssParseException.printStackTrace();
	}

	@Override
	public void error(CSSParseException cssParseException) throws CSSException {
		UDAParser.lineError.add(cssParseException.getLineNumber());
	}
}
