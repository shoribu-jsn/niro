/* Copyright © 2016- shoribu_jsn All Rights Reserved. */
package jp.co.shoribu_jsn.claire.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.LogRecord;

/**
 * 独自のFormatterです。
 * java.util.logging.SimpleFormatterと同様で、指定された LogRecord は、次を呼び出すようにフォーマットされます。<br>
 * <br>
 *     String.format(format, date, source, logger, level, message, thrown);<br>
 * <br>
 * <br>
 * 以下の通りです。<br>
 *   format - java.util.logging.SimpleFormatter.format プロパティーまたはデフォルトのフォーマットで指定された java.util.Formatter のフォーマット文字列。<br>
 *   date - ログレコードのイベント時間を表す、Date オブジェクト。<br>
 *   source - 使用可能な場合は、呼び出し元を表す文字列。使用できない場合は、ロガーの名前。<br>
 *   logger - ロガーの名前。<br>
 *   level - ログレベル。<br>
 *   message - Formatter.formatMessage(LogRecord) メソッドから返された、フォーマットされたログメッセージ。java.text のフォーマットを使用し、java.util.Formatter format 引数は使用しません。<br>
 *   thrown - ログレコードおよび改行文字で始まるそのバックトレースに関連付けられた throwable を表現する文字列 (存在する場合)。バックトレースがない場合は、空の文字列。<br>
 * 
 * @author rued97
 */
public class Formatter extends java.util.logging.Formatter {

	/** 使用するフォーマット */
	private final String format;

	/**
	 * フォーマットを指定指定してインスタンスを生成します。
	 * @param format 
	 */
	public Formatter(String format) {
		super();
		this.format = format;
	}

	@Override
	public String format(LogRecord record) {
		String source;
		if (record.getSourceClassName() != null) {
				source = record.getSourceClassName();
				if (record.getSourceMethodName() != null) {
					 source += " " + record.getSourceMethodName();
				}
		} else {
				source = record.getLoggerName();
		}
		String message = formatMessage(record);
		String throwable = "";
		if (record.getThrown() != null) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				pw.println();
				record.getThrown().printStackTrace(pw);
				pw.close();
				String crlf = System.getProperty("line.separator");
				throwable = crlf + sw.toString();
		}
		return String.format(this.format,
												 new Date(record.getMillis()),
												 source,
												 record.getLoggerName(),
												 record.getLevel(),
												 message,
												 throwable);
	}
}