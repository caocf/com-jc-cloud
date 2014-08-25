// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SerializationUtils.java

package org.jackychen.toolkits.utils;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import java.io.*;
import org.apache.commons.lang3.SerializationException;

public class SerializationUtils {
	public static Object clone(Serializable object) {
		return deserialize(serialize(object));
	}

	public static void serialize(Serializable obj, OutputStream outputStream) {
		if (outputStream == null) {
			throw new IllegalArgumentException(
					"The OutputStream must not be null");
		}
		HessianOutput out = null;
		try {
			out = new HessianOutput(outputStream);
			out.writeObject(obj);
		} catch (IOException ex) {
			throw new SerializationException(ex);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException ex) {
			}
		}
	}

	public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		serialize(obj, baos);
		return baos.toByteArray();
	}

	public static Object deserialize(InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException(
					"The InputStream must not be null");
		}
		HessianInput in = null;
		try {
			in = new HessianInput(inputStream);
			return in.readObject();
		} catch (IOException ex) {
			throw new SerializationException(ex);
		} finally {
			if (in != null)
				in.close();
		}
	}

	public static Object deserialize(byte[] objectData) {
		if (objectData == null) {
			throw new IllegalArgumentException("The byte[] must not be null");
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
		return deserialize(bais);
	}
}