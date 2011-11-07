/*
 *  soapUI, copyright (C) 2004-2011 eviware.com 
 *
 *  soapUI is free software; you can redistribute it and/or modify it under the 
 *  terms of version 2.1 of the GNU Lesser General Public License as published by 
 *  the Free Software Foundation.
 *
 *  soapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details at gnu.org.
 */

package com.eviware.soapui.impl.wsdl.teststeps.assertions.jms;

import com.eviware.soapui.config.TestAssertionConfig;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.submit.transports.jms.HermesJmsRequestTransport;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion;
import com.eviware.soapui.impl.wsdl.teststeps.assertions.AbstractTestAssertionFactory;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.AssertionError;
import com.eviware.soapui.model.testsuite.AssertionException;
import com.eviware.soapui.model.testsuite.RequestAssertion;
import com.eviware.soapui.model.testsuite.ResponseAssertion;

/**
 * Asserts JMS response within timeout
 * 
 * @author nebojsa.tasic
 */

public class JMSTimeoutAssertion extends WsdlMessageAssertion implements ResponseAssertion, RequestAssertion
{
	public static final String ID = "JMS Timeout";
	public static final String LABEL = "JMS Timeout";
	public static final String DESCRIPTION = "JMS Timeout desc...";

	public JMSTimeoutAssertion( TestAssertionConfig assertionConfig, Assertable assertable )
	{
		super( assertionConfig, assertable, false, false, false, true );
	}

	@Override
	protected String internalAssertResponse( MessageExchange messageExchange, SubmitContext context )
			throws AssertionException
	{
		Boolean temp = ( Boolean )context.getProperty( HermesJmsRequestTransport.IS_JMS_MESSAGE_RECEIVED );
		Boolean messageReceived = temp != null ? temp : false;

		Long timeout = ( Long )context.getProperty( HermesJmsRequestTransport.JMS_RECEIVE_TIMEOUT );
		if( messageReceived != null && !messageReceived )
		{
			throw new AssertionException( new AssertionError( "JMS Message timeout error! Message is not received in "
					+ timeout + " ms." ) );
		}

		return "JMS Timeout OK";
	}

	@Override
	protected String internalAssertRequest( MessageExchange messageExchange, SubmitContext context )
			throws AssertionException
	{
		return "JMS Timeout OK";
	}

	public static class Factory extends AbstractTestAssertionFactory
	{
		public Factory()
		{
			super( JMSTimeoutAssertion.ID, JMSTimeoutAssertion.LABEL, JMSTimeoutAssertion.class, WsdlRequest.class );
		}

		@Override
		public Class<? extends WsdlMessageAssertion> getAssertionClassType()
		{
			return JMSTimeoutAssertion.class;
		}
	}
}
