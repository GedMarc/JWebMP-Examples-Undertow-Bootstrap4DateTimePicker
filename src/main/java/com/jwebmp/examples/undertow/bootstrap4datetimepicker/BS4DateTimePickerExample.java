package com.jwebmp.examples.undertow.bootstrap4datetimepicker;

import com.jwebmp.core.Page;
import com.jwebmp.core.PlaceHolder;
import com.jwebmp.core.base.ajax.AjaxCall;
import com.jwebmp.core.base.ajax.AjaxResponse;
import com.jwebmp.core.base.html.Paragraph;
import com.guicedee.guicedinjection.GuiceContext;
import com.guicedee.logger.LogFactory;
import com.jwebmp.plugins.bs4.datetimepicker.BS4DateTimePicker;
import com.jwebmp.plugins.bs4.datetimepicker.options.BS4DateTimeViewModes;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import jakarta.servlet.ServletException;
import java.time.LocalDate;
import java.util.logging.Level;

public class BS4DateTimePickerExample
		extends Page<BS4DateTimePickerExample>
{
	public BS4DateTimePickerExample()
	{
		super("BS4 Date Time Picker - JWebSwing");
		add(new Paragraph("Below is an example of the Bootstrap 4 Date Time Picker utilizing the Tempus Dominus (BS4) Plugin"));

		BS4DateTimePicker<?> defaultPicker = new BS4DateTimePicker<>();
		defaultPicker.setID("defaultPicker");
		add(defaultPicker);

		BS4DateTimePicker<?> birthdaypicker = new BS4DateTimePicker<>();
		birthdaypicker.setID("birthdayPicker");
		birthdaypicker.getInput()
		              .bind("subscribe.birthDate");
		birthdaypicker.getOptions()
		              .setFormat("YYYY-MM-dd");
		birthdaypicker.getOptions()
		              .setMaxDate(LocalDate.now()
		                                   .minusYears(1));
		birthdaypicker.getInput()
		              .setRequired();
		birthdaypicker.getOptions()
		              .setViewMode(BS4DateTimeViewModes.Years);
		birthdaypicker.getInput()
		              .setPlaceholder("Birth Date");

		add(birthdaypicker);

		//Shortcut to paragraph
		getBody().add("The widget contains the entire API as found ");
		add(new PlaceHolder("dynamicInsertWidget"));

		BS4DateTimePicker<?> inputGroup = new BS4DateTimePicker();
		inputGroup.setID("testNoButton");
		inputGroup.setNoIcon();

		add(inputGroup);

	}

	/**
	 * This part runs the web site :)
	 *
	 * @param args
	 *
	 * @throws ServletException
	 */
	public static void main(String[] args) throws ServletException
	{
		LogFactory.configureConsoleColourOutput(Level.FINE);

		DeploymentInfo servletBuilder = Servlets.deployment()
		                                        .setClassLoader(BS4DateTimePickerExample.class.getClassLoader())
		                                        .setContextPath("/")
		                                        .setDeploymentName("helloworld.war");
		DeploymentManager manager = Servlets.defaultContainer()
		                                    .addDeployment(servletBuilder);
		manager.deploy();
		GuiceContext.inject();
		HttpHandler jwebSwingHandler = manager.start();
		Undertow server = Undertow.builder()
		                          .addHttpListener(6002, "localhost")
		                          .setHandler(jwebSwingHandler)
		                          .build();
		server.start();
	}

	@Override
	public AjaxResponse onConnect(AjaxCall call, AjaxResponse response)
	{
		return super.onConnect(call, response);
	}
}
