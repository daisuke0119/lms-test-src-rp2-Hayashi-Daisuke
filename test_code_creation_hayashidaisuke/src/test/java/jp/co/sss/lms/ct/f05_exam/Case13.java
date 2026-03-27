package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		//2022年10月2日(日)の「詳細」ボタンを押下
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(2).click();

		//ページが表示されるまで待機
		pageLoadTimeout(5);

		//正しく画面遷移が行われているかタイトルを取得し検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		WebElement headline = webDriver.findElement(By.tagName("h2"));
		assertEquals("2022年10月2日", headline.findElement(By.tagName("small")).getText());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {

		//セクション詳細画面の「本日の試験」エリアの「詳細」ボタンを押下
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(1).click();

		//ページが表示されるまで待機
		pageLoadTimeout(5);

		//正しく画面遷移が行われているかタイトルを取得し検証
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {

		//試験詳細画面の「試験を開始する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しく画面遷移が行われているかタイトルを取得し検証
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {

		//「確認画面へ進む」ボタンが表示されるまでスクロール
		scrollBy("1000");

		//「確認画面へ進む」ボタンを押下
		webDriver.findElements(By.cssSelector(".btn.btn-primary")).get(1).click();

		//ページが表示されるまで待機
		pageLoadTimeout(5);

		//未回答を知らせるメッセージが表示されているか検証
		assertTrue(webDriver.findElement(By.className("text-warning")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		// TODO ここに追加
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		// TODO ここに追加
	}

}
