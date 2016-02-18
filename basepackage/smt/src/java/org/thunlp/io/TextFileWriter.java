package org.thunlp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

public class TextFileWriter {
	BufferedWriter osw;

	public TextFileWriter(String filename) throws IOException {
		this(new File(filename), "UTF-8", false);
	}

	public TextFileWriter(String filename, boolean append) throws IOException {
		this(new File(filename), "UTF-8", append);
	}

	public TextFileWriter(File file) throws IOException {
		this(file, "UTF-8", false);
	}

	public TextFileWriter(File file, String charset) throws IOException {
		this(file, charset, false);
	}

	public TextFileWriter(String filename, String charset) throws IOException {
		this(new File(filename), charset, false);
	}

	public TextFileWriter(String filename, String charset, boolean append) throws IOException {
		this(new File(filename), charset, append);
	}

	public TextFileWriter(File file, String charset, boolean append) throws IOException {
		osw = constructWriter(file, charset, append);
	}

	public TextFileWriter(Path p) throws IOException {
		this(p, "UTF-8", false);
	}

	public TextFileWriter(Path p, String charset) throws IOException {
		this(p, charset, false);
	}

	public TextFileWriter(Path p, String charset, boolean append) throws IOException {
		osw = new BufferedWriter(
				new OutputStreamWriter(FileSystem.get(new JobConf()).create(p, !append /* overwrite */), charset));
	}

	protected BufferedWriter constructWriter(File file, String charset, boolean append) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charset));
	}

	public void write(String str) throws IOException {
		osw.write(str);
	}

	public void writeLine(String str) throws IOException {
		osw.write(str);
		osw.write('\n');
	}

	public void flush() throws IOException {
		osw.flush();
	}

	public void close() throws IOException {
		osw.close();
	}

	public void append(CharSequence cs) throws IOException {
		osw.append(cs);
	}

	public static void writeToFile(String content, File file, String encoding) throws IOException {
		TextFileWriter w = new TextFileWriter(file, encoding, false);
		w.write(content);
		w.close();
	}
}