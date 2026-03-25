package jp.co.sss.lms.ct.f03_report;

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
import org.openqa.selenium.JavascriptExecutor;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		//コース詳細画面で2022年10月2日(日)の「詳細」ボタンを押下
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(2).click();

		//タイトルが正しいか検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		//「提出済み週報を確認する」ボタンが表示される箇所までページをスクロール
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,1000)");

		//「提出済み週報を確認する」ボタンを押下
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(3).click();

		//タイトルが正しいか検証
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		//「提出する」ボタンが表示される箇所までページをスクロール
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,1000)");

		//テキストボックスの内容を消去する
		webDriver.findElement(By.id("content_1")).clear();

		//テキストボックスに修正済みの所感の内容を入力
		String fixedReport = "今週も頑張りました！\n来週も頑張ります！";
		webDriver.findElement(By.id("content_1")).sendKeys(fixedReport);

		//「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		//上部メニューの「ようこそ受講生AA1さん」のリンクをクリック
		webDriver.findElement(By.tagName("small")).click();

		//タイトルを取得して検証
		assertEquals("ユーザー詳細", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		//レポートの一覧が表示される箇所までページをスクロール
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,1000)");

		//先ほど編集した2022年10月2日(日)の項目のレポートの「詳細」ボタンをクリック
		webDriver.findElements(By.cssSelector(".btn.btn-default")).get(6).click();

		//正しく画面遷移が行われているかタイトルを取得して検証
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());

		//表示されているレポートの所感の内容が修正済みの内容に更新されているか検証
		String expectedReportText = "今週も頑張りました！\n来週も頑張ります！";
		assertEquals(expectedReportText, webDriver.findElements(By.tagName("td")).get(4).getText());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

}
