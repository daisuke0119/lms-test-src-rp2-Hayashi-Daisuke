package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

		//上部メニューの「ようこそ受講生AA1さん」のリンクをクリック
		webDriver.findElement(By.tagName("small")).click();

		//タイトルを取得して検証
		assertEquals("ユーザー詳細", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		//レポートの一覧が表示される個所までページをスクロール
		scrollBy("500");

		//2022年10月2日(日)の項目のレポートの「修正する」ボタンをクリック
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(7).click();

		//選択した日付の画面に遷移しているかタイトルと見出しを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		WebElement headline = webDriver.findElement(By.tagName("h2"));
		assertEquals("2022年10月2日", headline.findElement(By.tagName("small")).getText());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		//学習項目のテキストボックス内の文字列を消去
		webDriver.findElement(By.id("intFieldName_0")).clear();

		//「提出する」ボタンが表示される箇所までページをスクロール
		scrollBy("500");

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しくページがロードされるまで待機
		pageLoadTimeout(10);

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エラー表示状態で表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='intFieldName_0']")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		//エラー表示を発生させるため学習項目を入力しなおす
		webDriver.findElement(By.id("intFieldName_0")).sendKeys("ITリテラシー①");

		//理解度のドロップダウンリストで空白の選択肢を選択
		Select select = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		select.selectByIndex(0);

		//「提出する」ボタンが表示される箇所までページをスクロール
		scrollBy("500");

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しくページがロードされるまで待機
		pageLoadTimeout(10);

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エラー表示状態で表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='intFieldValue_0']")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		//理解度のドロップダウンリストで「2」の選択肢を選択
		Select select = new Select(webDriver.findElement(By.id("intFieldValue_0")));
		select.selectByIndex(2);

		//目標の達成度の項目に不適切な入力を行う
		WebElement achivementLevel = webDriver.findElement(By.id("content_0"));
		achivementLevel.clear();
		achivementLevel.sendKeys("five");

		//「提出する」ボタンが表示される箇所までページをスクロール
		scrollBy("500");

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しくページがロードされるまで待機
		pageLoadTimeout(10);

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エラー表示状態で表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='content_0']")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		//目標の達成度の項目に不適切な入力を行う
		WebElement achivementLevel = webDriver.findElement(By.id("content_0"));
		achivementLevel.clear();
		achivementLevel.sendKeys("50");

		//「提出する」ボタンが表示される箇所までページをスクロール
		scrollBy("500");

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しくページがロードされるまで待機
		pageLoadTimeout(10);

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エラー表示状態で表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='content_0']")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		//目標の達成度の項目の文字列を消去
		webDriver.findElement(By.id("content_0")).clear();

		//所感の項目の文字列を消去
		webDriver.findElement(By.id("content_1")).clear();

		//「提出する」ボタンが表示される箇所までページをスクロール
		scrollBy("500");

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しくページがロードされるまで待機
		pageLoadTimeout(10);

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エラー表示が確認できる位置までスクロール
		scrollBy("250");

		//エラー表示状態で表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='content_0']")).isDisplayed());
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='content_1']")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		//目標の達成度の項目に正しい文字列を入力(テスト項目範囲外の為)
		webDriver.findElement(By.id("content_0")).sendKeys("5");

		//テストを行う項目が表示される箇所までページをスクロール
		scrollBy("500");

		//テスト用の不適切な文字列を用意
		String wrongText = "a".repeat(2001);

		//所感の項目に用意した不適切な文字列を入力
		//入力の処理に時間がかかるため明示的処理を追加
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement impressions = webDriver.findElement(By.id("content_1"));
		impressions.sendKeys(wrongText);
		wait.until(ExpectedConditions.textToBePresentInElementValue(impressions, wrongText));

		//一週間の振り返りの項目を消去し、用意した不適切な文字列を入力
		WebElement weeklyReview = webDriver.findElement(By.id("content_2"));
		weeklyReview.clear();
		weeklyReview.sendKeys(wrongText);
		wait.until(ExpectedConditions.textToBePresentInElementValue(weeklyReview, wrongText));

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しくページがロードされるまで待機
		pageLoadTimeout(10);

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//エラー表示が確認できる箇所までスクロール
		scrollBy("300");

		//エラー表示状態で表示されているか検証
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='content_1']")).isDisplayed());
		assertTrue(webDriver.findElement(By.cssSelector(".errorInput[id='content_2']")).isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

}
