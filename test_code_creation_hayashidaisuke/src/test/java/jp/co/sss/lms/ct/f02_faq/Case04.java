package jp.co.sss.lms.ct.f02_faq;

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

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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

}