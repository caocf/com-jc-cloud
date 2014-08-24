/**  
 * Description: 文件操作类
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */  
package com.jc.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈文件公共操作类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class FileUtils{
	/**
	 * 创建空文件
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @throws IOException

	
	 */
	public static void newFile(String path) throws IOException {
		new File(path).createNewFile();
	}

	/**
	 * 创建文件并写入字符串
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @param content
	 * @throws IOException

	
	 */
	public static void newFile(String path, String content) throws IOException {
		File myfile = new File(path);
		if (!myfile.exists()) {
			myfile.createNewFile();
		}
		FileWriter fw = new FileWriter(myfile);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		fw.close();
	}

	/**
	 * 写入字符串到文件
	 * 〈功能详细描述〉
	 *
	 * @param filePath
	 * @param content
	 * @param append
	 * @throws IOException

	
	 */
	public static void writeFile(String filePath, String content, boolean append)
			throws IOException {
		File myfile = new File(filePath);
		if (!append) {
			myfile.delete();
			myfile.createNewFile();
		}
		FileWriter fw = new FileWriter(filePath, true);
		fw.write(content);
		fw.close();
	}

	/**
	 * 读取文件，把文件内容转换为字符串（读取超大文件要慎重）
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @return
	 * @throws IOException

	
	 */
	public static String readFile(String path) throws IOException {
		FileReader fr = new FileReader(path);
		File myfile = new File(path);
		int fileLen = (int) myfile.length();
		char[] buf = new char[fileLen];
		int len = fr.read(buf);
		String msg = new String(buf, 0, len);
		fr.close();
		return msg;
	}

	/**
	 * 删除文件
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @throws IOException

	
	 */
	public static void delFile(String path) throws IOException {
		File myfile = new File(path);
		if (!myfile.isDirectory())
			myfile.delete();
	}

	/**
	 * 删除文件
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @return
	 * @throws IOException

	
	 */
	public static List readFileByLine(String path) throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		List list = new ArrayList();
		while (br.readLine() != null) {
			list.add(br.readLine());
		}
		br.close();
		fr.close();
		return list;
	}

	/**
	 * 按UTF-8编码读取内容
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @return
	 * @throws IOException

	
	 */
	public static String readFileByUTF8(String path) throws IOException {
		String content = "";
		File f = new File(path);
		InputStreamReader read = new InputStreamReader(new FileInputStream(f),
				"UTF-8");

		BufferedReader reader = new BufferedReader(read);
		String line;
		while ((line = reader.readLine()) != null) {
			content = content + line + "\n";
		}
		return content;
	}

	/**
	 * 按utf-8编码保存文件
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @param content
	 * @throws IOException

	
	 */
	public static void saveFileByUTF8(String path, String content)
			throws IOException {
		File myfile = new File(path);
		myfile.delete();
		myfile.createNewFile();

		FileOutputStream fos = new FileOutputStream(myfile.getAbsoluteFile());
		Writer w = new OutputStreamWriter(fos, "UTF-8");
		w.write(content);
		w.close();
		fos.close();
	}


	/**
	 * 新建目录 (支持创建多层目录)
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @throws IOException

	
	 */
	public static void newFolder(String path) throws IOException {
		File mydir = new File(path);
		if (!mydir.exists())
			mydir.mkdirs();
	}

	
	/**
	 * 字符串转换到流
	 * 〈功能详细描述〉
	 *
	 * @param srcStr
	 * @return

	
	 */
	public static InputStream string2stream(String srcStr) {
		InputStream is = null;
		if (!srcStr.trim().equals("")) {
			String str = srcStr;
			is = new ByteArrayInputStream(str.getBytes());
		}
		return is;
	}

	/**
	 * 流转换到字符串
	 * 〈功能详细描述〉
	 *
	 * @param srcStream
	 * @return
	 * @throws IOException

	
	 */
	public static String stream2string(InputStream srcStream)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(srcStream));
		StringBuffer buf = new StringBuffer();
		String line = "";
		while ((line = br.readLine()) != null) {
			buf.append(line + "\n");
		}
		return buf.toString();
	}


	/**
	 * 把文件内容转换为流
	 * 〈功能详细描述〉
	 *
	 * @param path
	 * @return
	 * @throws IOException

	
	 */
	public static InputStream getStreamInFile(String path) throws IOException {
		File myfile = new File(path);
		InputStream is = new FileInputStream(myfile);
		return is;
	}
	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。 <b>目前这个方法的行为方式还不稳定， 主要是方法有些信息输出，这些信息输出是否保留还在考
	 * 
	 * 虑中。</b>
	 * 
	 * @param file
	 *            需要修改最后访问时间的文件。
	 * @since 0.1
	 */
	public static void touch(File file) {
		long currentTime = System.currentTimeMillis();
		if (!file.exists()) {
			System.err.println("file not found:" + file.getName());
			System.err.println("Create a new file:" + file.getName());
			try {
				if (file.createNewFile()) {
					// System.out.println("Succeeded!");
				} else {
					// System.err.println("Create file failed!");
				}
			} catch (IOException e) {
				// System.err.println("Create file failed!");
				e.printStackTrace();
			}
		}
		boolean result = file.setLastModified(currentTime);
		if (!result) {
			// System.err.println("touch failed: " + file.getName());
		}
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。 <b>目前这个方法的行为方式还不稳定， 主要是方法有些信息输出，这些信息输出是否保留还在考
	 * 
	 * 虑中。</b>
	 * 
	 * @param fileName
	 *            需要修改最后访问时间的文件的文件名。
	 * @since 0.1
	 */
	public static void touch(String fileName) {
		File file = new File(fileName);
		touch(file);
	}

	/**
	 * 判断指定的文件是否存在。
	 * 
	 * @param fileName
	 *            要判断的文件的文件名
	 * @return 存在时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean isFileExist(String fileName) {
		return new File(fileName).isFile();
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 * 
	 * @param file
	 *            要创建的目录
	 * @return 完全创建成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean makeDirectory(File file) {
		File parent = file.getParentFile();
		if (parent != null) {
			return parent.mkdirs();
		}
		return false;
	}

	/**
	 * 修改文件的最后访问时间。 如果文件不存在则创建该文件。 <b>目前这个方法的行为方式还不稳定，主要是方法有些信息输出，这些信息输出是否保留还在考
	 * 虑中。</b>
	 * 
	 * @param files
	 *            需要修改最后访问时间的文件数组。
	 * @since 0.1
	 */
	public static void touch(File[] files) {
		for (int i = 0; i < files.length; i++) {
			touch(files);
		}
	}

	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 * 
	 * @param fileName
	 *            要创建的目录的目录名
	 * @return 完全创建成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean makeDirectory(String fileName) {
		File file = new File(fileName);
		return makeDirectory(file);
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 * @since 0.1
	 */
	public static boolean emptyDirectory(File directory) {
		boolean result = false;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				return result;
			}
		}
		return true;
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directoryName
	 *            要清空的目录的目录名
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean emptyDirectory(String directoryName) {
		File dir = new File(directoryName);
		return emptyDirectory(dir);
	}

	/**
	 * 将字节流转换成字符串返回
	 * 
	 * @param is
	 *            输入流
	 * @return 字符串
	 */
	public static String readFileByLines(InputStream is) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将文件一行一行的读成List返回
	 * 
	 * @param file
	 *            需要读取的文件
	 * @return 文件的一行就是一个List的Item的返回
	 */
	public static List<String> readFileToList(File file) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	/**
	 * 将文件按照一定的编码方式一行一行的读成List返回
	 * 
	 * @param file
	 *            需要读取的文件
	 * @param encodType
	 *            字符编码
	 * @return 文件的一行就是一个List的Item的返回
	 */
	public static List<String> readFileToList(File file, String encodType) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encodType));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (!(tempString.charAt(0) >= 'a' && tempString.charAt(0) <= 'z'))
					tempString = tempString.substring(1);
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	/**
	 * 将指定的字符串内容以指定的方式写入到指定的文件中
	 * 
	 * @param file
	 *            需要写人的文件
	 * @param content
	 *            需要写入的内容
	 * @param flag
	 *            是否追加写入
	 */
	public static void writeFile(File file, String content, Boolean flag) {
		try {
			if (!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file, flag);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将指定的字符串内容以指定的方式及编码写入到指定的文件中
	 * 
	 * @param file
	 *            需要写人的文件
	 * @param content
	 *            需要写入的内容
	 * @param flag
	 *            是否追加写入
	 * @param encodType
	 *            文件编码
	 */
	public static void writeFile(File file, String content, Boolean flag,
			String encodType) {
		try {
			FileOutputStream writerStream = new FileOutputStream(file, flag);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					writerStream, encodType));
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝文件夹
	 * 
	 * @param oldPath
	 *            源目录
	 * @param newPath
	 *            目标目录
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将文件重命名
	 * 
	 * @param oldName
	 *            源文件名
	 * @param newName
	 *            新文件名
	 */
	public static void reName(String oldName, String newName) {
		File oldF = new File(oldName);
		File newF = new File(newName);
		oldF.renameTo(newF);
	}

	/**
	 * 将一个文件列表文件中所有文件拷贝到指定目录中
	 * 
	 * @param listFile
	 *            包含需要拷贝的文件的列表的文件，每个文件写在一行
	 * @param targetFloder
	 *            目标目录
	 */
	public static void copyFilesFromList(String listFile, String targetFloder) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(listFile));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				copyFile(tempString, targetFloder);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param oldPath
	 *            源文件
	 * @param newPath
	 *            目标文件
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			File temp = new File(oldPath);
			FileInputStream input = new FileInputStream(temp);
			FileOutputStream output = new FileOutputStream(newPath + "/"
					+ (temp.getName()).toString());
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件列表
	 * 
	 * @param files
	 *            需要删除的文件/文件夹列表
	 * @return 删除成功true，否则返回false
	 */
	public static boolean deleteFiles(List<String> files) {
		boolean flag = true;
		for (String file : files) {
			flag = delete(file);
			if (!flag)
				break;
		}
		return flag;
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile())
			return file.delete();
		return false;
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录路径
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		if ((!dirFile.exists()) || (!dirFile.isDirectory()))
			return false;
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		return dirFile.delete();
	}

	/**
	 * 删除指定目录及其中的所有内容。
	 * 
	 * @param dir
	 *            要删除的目录
	 * @return 删除成功时返回true，否则返回false。
	 * @since 0.1
	 */
	public static boolean deleteDirectory(File dir) {
		if ((dir == null) || !dir.isDirectory()) {
			throw new IllegalArgumentException("Argument " + dir
					+ " is not a directory. ");
		}

		File[] entries = dir.listFiles();
		int sz = entries.length;
		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else {
				if (!entries[i].delete()) {
					return false;
				}
			}
		}

		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	/**
	 * 返回文件的URL地址。
	 * 
	 * @param file
	 *            文件
	 * @return 文件对应的的URL地址
	 * @throws MalformedURLException
	 * @since 0.4
	 * @deprecated 在实现的时候没有注意到File类本身带一个toURL方法将文件路径转换为URL。 请使用File.toURL方法。
	 */
	public static URL getURL(File file) throws MalformedURLException {
		String fileURL = "file:/" + file.getAbsolutePath();
		URL url = new URL(fileURL);
		return url;
	}

	/**
	 * 从文件路径得到文件名。
	 * 
	 * @param filePath
	 *            文件的路径，可以是相对路径也可以是绝对路径
	 * @return 对应的文件名
	 * @since 0.4
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 从文件名得到文件绝对路径。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 对应的文件路径
	 * @since 0.4
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
	 * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
	 * 
	 * @param filePath
	 *            转换前的路径
	 * @return 转换后的路径
	 * @since 0.4
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace('\\', '/');
	}

	/**
	 * 从文件名得到UNIX风格的文件绝对路径。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 对应的UNIX风格的文件路径
	 * @since 0.4
	 * @see #toUNIXpath(String filePath) toUNIXpath
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXpath(file.getAbsolutePath());
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param file
	 *            文件
	 * @return 文件名中的类型部分
	 * @since 0.5
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的名字部分
	 * @since 0.5
	 */
	public static String getNamePart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return fileName;
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				if (length == 1) {
					return fileName;
				} else {
					return fileName.substring(0, point);
				}
			} else {
				return fileName.substring(secondPoint + 1, point);
			}
		} else {
			return fileName.substring(point + 1);
		}
	}

	/**
	 * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
	 * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 父路径，不存在或者已经是父目录时返回""
	 * @since 0.5
	 */
	public static String getPathPart(String fileName) {
		int point = getPathLsatIndex(fileName);
		int length = fileName.length();
		if (point == -1) {
			return "";
		} else if (point == length - 1) {
			int secondPoint = getPathLsatIndex(fileName, point - 1);
			if (secondPoint == -1) {
				return "";
			} else {
				return fileName.substring(0, secondPoint);
			}
		} else {
			return fileName.substring(0, point);
		}
	}

	/**
	 * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中首次出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathIndex(String fileName) {
		int point = fileName.indexOf('/');
		if (point == -1) {
			point = fileName.indexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置后首次出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathIndex(String fileName, int fromIndex) {
		int point = fileName.indexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.indexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 路径分隔符在路径中最后出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathLsatIndex(String fileName) {
		int point = fileName.lastIndexOf('/');
		if (point == -1) {
			point = fileName.lastIndexOf('\\');
		}
		return point;
	}

	/**
	 * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
	 * 
	 * @param fileName
	 *            文件路径
	 * @param fromIndex
	 *            开始查找的位置
	 * @return 路径分隔符在路径中指定位置前最后出现的位置，没有出现时返回-1。
	 * @since 0.5
	 */
	public static int getPathLsatIndex(String fileName, int fromIndex) {
		int point = fileName.lastIndexOf('/', fromIndex);
		if (point == -1) {
			point = fileName.lastIndexOf('\\', fromIndex);
		}
		return point;
	}

	/**
	 * 将文件名中的类型部分去掉。
	 * 
	 * @param filename
	 *            文件名
	 * @return 去掉类型部分的结果
	 * @since 0.5
	 */
	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 * 
	 * @param pathName
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 * @since 0.5
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}
}