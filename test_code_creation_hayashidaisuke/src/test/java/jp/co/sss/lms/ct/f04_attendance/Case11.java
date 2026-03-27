package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		//ログインページを表示
		goTo("http://localhost:8080/lms");

		//タイトルが正しいか検証
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		//ログインIDを入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");

		//パスワードを入力
		webDriver.findElement(By.id("password")).sendKeys("PasswordAA01");

		//ログインボタンをクリック
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//タイトルが正しいか検証
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		//ヘッダーの「勤怠」リンクをクリック
		webDriver.findElement(By.partialLinkText("勤怠")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//ページが表示されるまで待機
		pageLoadTimeout(10);

		//正しい画面遷移が行われているかタイトルを取得し、検証
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {

		//「勤怠情報を直接編集する」リンクをクリックする
		webDriver.findElement(By.partialLinkText("勤怠情報を直接編集する")).click();

		//ページが表示されるまで待機
		pageLoadTimeout(10);

		//「定時」ボタンが表示されているかどうか検証
		assertTrue(webDriver.findElement(By.cssSelector("button.btn.btn-success")).getText().contains("定時"));

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

		//2026年3月25日(水)の出退勤の時刻を
		//各項目のドロップダウンリストを用いて入力
		Select startH = new Select(webDriver.findElement(By.id("startHour0")));
		Select startM = new Select(webDriver.findElement(By.id("startMinute0")));
		Select endH = new Select(webDriver.findElement(By.id("endHour0")));
		Select endM = new Select(webDriver.findElement(By.id("endMinute0")));

		startH.selectByVisibleText("09");
		startM.selectByVisibleText("00");
		endH.selectByVisibleText("18");
		endM.selectByVisibleText("00");

		//2026年3月26日(木)の出退勤の時刻を「定時」ボタンで入力
		webDriver.findElements(By.cssSelector("button.btn.btn-success")).get(1).click();

		//「更新」ボタンが表示されるまでスクロール
		scrollBy("500");

		//「更新」ボタンを押下
		webDriver.findElement(By.cssSelector("input.btn[value = '更新']")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//正しく画面遷移が行われているか画面要素を取得して検証
		WebElement targetTag = webDriver.findElement(By.cssSelector("div[role = 'alert']"));
		assertEquals("勤怠情報の登録が完了しました。", targetTag.findElements(By.tagName("span")).get(1).getText());

		//2026年3月25日(水)の出退勤の時刻が正しく入力されているか確認
		String targetDayBefore = "2026年3月25日(水)";
		String dayBeforeStartTimeXPath = "//td[contains(text(),'" + targetDayBefore + "')]/parent::tr/td[3]";
		String dayBeforeEndTimeXPath = "//td[contains(text(),'" + targetDayBefore + "')]/parent::tr/td[4]";

		assertEquals("09:00", webDriver.findElement(By.xpath(dayBeforeStartTimeXPath)).getText());
		assertEquals("18:00", webDriver.findElement(By.xpath(dayBeforeEndTimeXPath)).getText());

		//2026年3月26日(木)の出退勤の時刻が正しく入力されているか確認
		String targetOnTheDay = "2026年3月26日(木)";
		String onTheDayStartTimeXPath = "//td[contains(text(),'" + targetOnTheDay + "')]/parent::tr/td[3]";
		String onTheDayEndTimeXPath = "//td[contains(text(),'" + targetOnTheDay + "')]/parent::tr/td[4]";

		assertEquals("09:00", webDriver.findElement(By.xpath(onTheDayStartTimeXPath)).getText());
		assertEquals("18:00", webDriver.findElement(By.xpath(onTheDayEndTimeXPath)).getText());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

}
