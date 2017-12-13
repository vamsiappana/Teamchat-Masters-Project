package se.bth.aspm.groupone.teamchat.client.utils;

import java.util.Comparator;

import se.bth.aspm.groupone.teamchat.client.model.Channel;

public class Comparators {

	public static final Comparator<Channel> CHANNEL_COMPARATOR = new Comparator<Channel>() {

		@Override
		public int compare(Channel ch1, Channel ch2) {
			if (ch1.getChannelType().ordinal() < ch2.getChannelType().ordinal()) {
				return -1;
			} else if (ch1.getChannelType().ordinal() > ch2.getChannelType().ordinal()) {
				return 1;
			} else {
				return ch1.getName().compareToIgnoreCase(ch2.getName());
			}
		}

	};

	public static final Comparator<String> NAMES_COMPARATOR = new Comparator<String>() {

		@Override
		public int compare(String name1, String name2) {
			return name1.compareToIgnoreCase(name2);
		}
	};

}
