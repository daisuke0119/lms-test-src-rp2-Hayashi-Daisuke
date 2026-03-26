package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		//「出勤」ボタンを押下
		webDriver.findElement(By.cssSelector("[name = 'punchIn'].btn")).click();

		//ポップアップが表示されるため「OK」をクリック
		webDriver.switchTo().alert().accept();

		//正しい時刻(テスト実施時刻)が登録されているか検証
		String targetDate = "2026年3月26日(木)";
		String xpath = "//td[contains(text(),'" + targetDate + "')]/parent::tr/td[3]";
		String expectedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		assertEquals(expectedTime, webDriver.findElement(By.xpath(xpath)).getText());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		//「出勤」ボタンを押下
		webDriver.findElement(By.cssSelector("[name = 'punchOut'].btn")).click();

		//ポップアップが表示されるため「OK」をクリック
		webDriver.switchTo().alert().accept();

		//正しい時刻(テスト実施時刻)が登録されているか検証
		String targetDate = "2026年3月26日(木)";
		String xpath = "//td[contains(text(),'" + targetDate + "')]/parent::tr/td[4]";
		String expectedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		assertEquals(expectedTime, webDriver.findElement(By.xpath(xpath)).getText());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});

	}

}
