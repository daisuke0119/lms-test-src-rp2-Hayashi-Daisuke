package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		//「機能」をクリックしてドロップダウンリストを表示
		webDriver.findElement(By.className("dropdown-toggle")).click();

		//「ヘルプ」をクリック
		webDriver.findElement(By.partialLinkText("ヘルプ")).click();

		//タイトルが正しいか検証
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		//現在のウィンドウハンドルを取得
		String presentPage = webDriver.getWindowHandle();

		//「よくある質問」をクリック
		webDriver.findElement(By.partialLinkText("よくある質問")).click();

		//別タグが開かれているか検証
		int pageQuantity = webDriver.getWindowHandles().size();
		assertEquals(2, pageQuantity);

		//新しいタブに切り替え
		for (String newPage : webDriver.getWindowHandles()) {
			if (!presentPage.contentEquals(newPage)) {
				webDriver.switchTo().window(newPage);
				break;
			}
		}

		//新しいタブでタイトルが正しいか検証
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		//カテゴリ検索を行う(・【研修関係】を選択)
		webDriver.findElement(By.partialLinkText("【研修関係】")).click();

		//検索結果が表示される個所までページをスクロール
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,500)");

		//表示されている検索結果が正しいか要素を取得
		List<WebElement> faqList = webDriver.findElements(By.className("mb10"));
		int expectedNum = 2;
		//取得した要素数が期待する数なら一致しているか検証
		int faqQuantity = webDriver.findElements(By.className("mb10")).size();
		if (faqQuantity == expectedNum) {
			assertEquals("Q.キャンセル料・途中退校について", faqList.get(0).getText());
			assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", faqList.get(1).getText());
		}

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

		//検索結果から質問をクリックし、正しい回答が表示されるか検証
		webDriver.findElement(By.className("mb10")).click();
		WebElement expectedStr = webDriver.findElement(By.className("fs18"));
		assertTrue(expectedStr.isDisplayed());

		//スクリーンショットを撮影
		getEvidence(new Object() {
		});
	}

}
