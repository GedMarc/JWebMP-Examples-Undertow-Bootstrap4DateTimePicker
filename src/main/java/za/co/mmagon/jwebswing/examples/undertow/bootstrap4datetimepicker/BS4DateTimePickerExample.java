package za.co.mmagon.jwebswing.examples.undertow.bootstrap4datetimepicker;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import za.co.mmagon.guiceinjection.GuiceContext;
import za.co.mmagon.jwebswing.Page;
import za.co.mmagon.jwebswing.PlaceHolder;
import za.co.mmagon.jwebswing.base.ajax.AjaxCall;
import za.co.mmagon.jwebswing.base.ajax.AjaxResponse;
import za.co.mmagon.jwebswing.base.html.Div;
import za.co.mmagon.jwebswing.base.html.Paragraph;
import za.co.mmagon.jwebswing.plugins.bs4datetimepicker.BS4DateTimePicker;
import za.co.mmagon.jwebswing.plugins.bs4datetimepicker.BS4DateTimePickerInputGroup;
import za.co.mmagon.jwebswing.plugins.bs4datetimepicker.options.BS4DateTimeToolbarPlacements;
import za.co.mmagon.jwebswing.plugins.bs4datetimepicker.options.BS4DateTimeViewModes;
import za.co.mmagon.jwebswing.plugins.bs4datetimepicker.options.BS4DateTimeWidgetPositions;
import za.co.mmagon.logger.LogFactory;
import za.co.mmagon.logger.handlers.ConsoleSTDOutputHandler;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BS4DateTimePickerExample extends Page
{
	public BS4DateTimePickerExample()
	{
		super("BS4 Date Time Picker - JWebSwing");
		add(new Paragraph("Below is an example of the Bootstrap 4 Date Time Picker utilizing the Tempus Dominus (BS4) Plugin"));

		BS4DateTimePickerInputGroup birthdaypicker = new BS4DateTimePickerInputGroup("subscribe.birthDate");
		birthdaypicker.getOptions().setFormat("YYYY-MM-dd");
		birthdaypicker.getOptions().setMaxDate(LocalDate.now().minusYears(18l));
		birthdaypicker.getOptions().setViewMode(BS4DateTimeViewModes.Years);
		birthdaypicker.setRequired(true);
		birthdaypicker.getInput().setPlaceholder("Birth Date");
		birthdaypicker.bind("subscribe.birthDate");

		add(birthdaypicker);

		//Shortcut to paragraph
		getBody().add("The widget contains the entire API as found ");
		add(new PlaceHolder("dynamicInsertWidget"));
	}

	@Override
	public AjaxResponse onConnect(AjaxCall call, AjaxResponse response)
	{
		BS4DateTimePicker pickerDynamic = new BS4DateTimePicker();
		//No ID is required
		add(pickerDynamic);
		/*pickerDynamic.getOptions()
				.setDate(LocalDate.now().plus(1,ChronoUnit.YEARS))
				.setAllowMultidate(true)
				.setToolbarPlacement(BS4DateTimeToolbarPlacements.Top)
				.setMinDate(LocalDate.now()
						            .minus(3, ChronoUnit.MONTHS))
				.getWidgetPositioning()
				.setVertical(BS4DateTimeWidgetPositions.top);
*/
		//Add the picker to the place holder created on the static page
		Div d= new Div();
		d.setID("dynamicInsertWidget");
		d.add(pickerDynamic);
		response.addComponent(d);

		return super.onConnect(call, response);
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
		Handler[] handles = Logger.getLogger("")
				                    .getHandlers();
		for (Handler handle : handles)
		{
			handle.setLevel(Level.FINE);
		}
		LogFactory.setDefaultLevel(Level.FINE);
		Logger.getLogger("")
				.addHandler(new ConsoleSTDOutputHandler(true));

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
}
