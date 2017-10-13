package com.netelastic;

import org.testng.annotations.Test;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import resources.base;
import org.monte.screenrecorder.ScreenRecorder;
import org.monte.media.math.Rational;
import org.monte.media.Format;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class dragAndDrop extends base {
	private static ScreenRecorder screenRecorder;

	@BeforeTest
	public void testSetup() throws IOException, AWTException {
		driver = initializeDriver();
		driver.manage().window().maximize();

		// Create a instance of GraphicsConfiguration to get the Graphics configuration
		// of the Screen. This is needed for ScreenRecorder class.
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		// Create a instance of ScreenRecorder with the required configurations
		screenRecorder = new ScreenRecorder(gc, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, (int) 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, (int) (15 * 60)),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
				null);

		screenRecorder.start();
	}

	@Test
	public void dragAndDropTest() throws InterruptedException {
		driver.get("http://blacklabel.github.io/bubble_dragndrop/");
		WebElement sourceSvg = driver.findElement(By.xpath(
				"//div[starts-with(@id,'highcharts')]/*[name()='svg']/*[name()='g'][6]/*[name()='g'][3]/*[name()='path'][2]"));

		WebElement destSvg = driver.findElement(By.xpath(
				"//div[starts-with(@id,'highcharts')]/*[name()='svg']/*[name()='g'][6]/*[name()='g'][1]/*[name()='path'][2]"));

		WebElement sourceSvg1 = driver.findElement(By.xpath(
				"//div[starts-with(@id,'highcharts')]/*[name()='svg']/*[name()='g'][6]/*[name()='g'][5]/*[name()='path'][3]"));
		WebElement destSvg1 = driver.findElement(By.xpath(
				"//div[starts-with(@id,'highcharts')]/*[name()='svg']/*[name()='g'][6]/*[name()='g'][1]/*[name()='path'][3]"));

		WebElement sourceSvg2 = driver.findElement(By.xpath(
				"//div[starts-with(@id,'highcharts')]/*[name()='svg']/*[name()='g'][6]/*[name()='g'][3]/*[name()='path'][2]"));
		Actions act = new Actions(driver);
		Thread.sleep(2000);
		act.dragAndDrop(destSvg, sourceSvg).build().perform();
		Thread.sleep(2000);
		act.dragAndDrop(sourceSvg1, destSvg1).build().perform();
		Thread.sleep(2000);
		act.dragAndDrop(sourceSvg2, destSvg1).build().perform();
		Thread.sleep(2000);
		act.dragAndDrop(destSvg, sourceSvg1).build().perform();
		Thread.sleep(2000);
		act.dragAndDrop(destSvg, sourceSvg2).build().perform();
		Thread.sleep(2000);
		act.dragAndDrop(destSvg1, sourceSvg).build().perform();
	}

	@AfterTest
	public void teardown() throws IOException {
		driver.close();
		driver = null;
		screenRecorder.stop();
		List<File> createdMovieFiles = screenRecorder.getCreatedMovieFiles();
		for (File movie : createdMovieFiles) {
			System.out.println("New movie created: " + movie.getAbsolutePath());
		}
	}

}
