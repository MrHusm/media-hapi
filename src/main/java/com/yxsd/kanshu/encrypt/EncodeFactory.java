package com.yxsd.kanshu.encrypt;

public class EncodeFactory {

	public enum Version {
		VERSION_1("1.1"), VERSION_2("1.2"), VERSION_3("1.3"), VERSION_4("1.4");
		private String version;

		private Version(String version) {
			this.version = version;
		}

		public String getVersion() {
			return this.version;
		}
	}

	public static IEncode getEncode(Version version) {
		switch (version) {
		case VERSION_1: {
			return new EncodeMagicWhatImpl(1);
		}
		case VERSION_2: {
			return new EncodeMagicWhatImpl(2);
		}
		case VERSION_3: {
			return new EncodeMagicImpl();
		}
		case VERSION_4: {
			return new EncodeMagicWhyImpl();
		}
		}
		return new EncodeMagicImpl();
	}

}
