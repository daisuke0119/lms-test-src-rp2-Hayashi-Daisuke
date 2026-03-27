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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {

		//2026年3月25日(水)の出退勤の項目に不適切な入力を行う
		Select startH = new Select(webDriver.findElement(By.id("startHour0")));
		Select startM = new Select(webDriver.findElement(By.id("startMinute0")));
		Select endH = new Select(webDriver.findElement(By.id("endHour0")));
		Select endM = new Select(webDriver.findElement(By.id("endMinute0")));

		startH.selectByIndex(0);
		startM.selectByVisibleText("00");
		endH.selectByVisibleText("18");
		endM.selectByIndex(0);

		//「更新」ボタンが表示されるまでスクロール
		scrollBy("500");

		//「更新」ボタンを押下
		webDriver.findElement(By.cssSelector("input.btn[value = '更新']")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//エラーメッセージが表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).isDisplayed());

		//表示されているエラーメッセージが正しいか検証
		assertTrue(webDriver.findElements(By.cssSelector(".help-inline.error")).get(0).getText().contains("出勤時間"));
		assertTrue(webDriver.findElements(By.cssSelector(".help-inline.error")).get(1).getText().contains("退勤時間"));
		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

		//2026年3月25日(水)の出退勤の項目に不適切な入力を行う
		Select startH = new Select(webDriver.findElement(By.id("startHour0")));
		Select startM = new Select(webDriver.findElement(By.id("startMinute0")));
		Select endH = new Select(webDriver.findElement(By.id("endHour0")));
		Select endM = new Select(webDriver.findElement(By.id("endMinute0")));

		startH.selectByIndex(0);
		startM.selectByIndex(0);
		endH.selectByVisibleText("18");
		endM.selectByVisibleText("00");

		//「更新」ボタンが表示されるまでスクロール
		scrollBy("500");

		//「更新」ボタンを押下
		webDriver.findElement(By.cssSelector("input.btn[value = '更新']")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//エラーメッセージが表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).isDisplayed());

		//表示されているエラーメッセージが正しいか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).getText().contains("出勤情報"));

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {

		//2026年3月25日(水)の出退勤の項目に不適切な入力を行う
		Select startH = new Select(webDriver.findElement(By.id("startHour0")));
		Select startM = new Select(webDriver.findElement(By.id("startMinute0")));
		Select endH = new Select(webDriver.findElement(By.id("endHour0")));
		Select endM = new Select(webDriver.findElement(By.id("endMinute0")));

		startH.selectByVisibleText("19");
		startM.selectByVisibleText("00");
		endH.selectByVisibleText("18");
		endM.selectByVisibleText("00");

		//「更新」ボタンが表示されるまでスクロール
		scrollBy("500");

		//「更新」ボタンを押下
		webDriver.findElement(By.cssSelector("input.btn[value = '更新']")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//エラーメッセージが表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).isDisplayed());

		//表示されているエラーメッセージが正しいか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).getText().contains("出勤時刻"));
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).getText().contains("退勤時刻"));

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		//2026年3月25日(水)の出退勤、中抜けの項目に不適切な入力を行う
		Select startH = new Select(webDriver.findElement(By.id("startHour0")));
		Select startM = new Select(webDriver.findElement(By.id("startMinute0")));
		Select endH = new Select(webDriver.findElement(By.id("endHour0")));
		Select endM = new Select(webDriver.findElement(By.id("endMinute0")));
		Select stepOutTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));

		startH.selectByVisibleText("17");
		startM.selectByVisibleText("00");
		endH.selectByVisibleText("18");
		endM.selectByVisibleText("00");
		stepOutTime.selectByVisibleText("2時間");

		//「更新」ボタンが表示されるまでスクロール
		scrollBy("500");

		//「更新」ボタンを押下
		webDriver.findElement(By.cssSelector("input.btn[value = '更新']")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//エラーメッセージが表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).isDisplayed());

		//表示されているエラーメッセージが正しいか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).getText().contains("中抜け時間"));

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {

		String wrongText = "a".repeat(101);

		//2026年3月25日(水)の出退勤の時刻を「定時」ボタンで入力
		webDriver.findElements(By.cssSelector("button.btn.btn-success")).get(0).click();

		//備考欄に不適切な内容を入力
		webDriver.findElement(By.name("attendanceList[0].note")).sendKeys(wrongText);

		//「更新」ボタンが表示されるまでスクロール
		scrollBy("500");

		//「更新」ボタンを押下
		webDriver.findElement(By.cssSelector("input.btn[value = '更新']")).click();

		//ポップアップが表示されるので「OK」をクリック
		webDriver.switchTo().alert().accept();

		//エラーメッセージが表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).isDisplayed());

		//表示されているエラーメッセージが正しいか検証
		assertTrue(webDriver.findElement(By.cssSelector(".help-inline.error")).getText().contains("備考の長さ"));

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

}
