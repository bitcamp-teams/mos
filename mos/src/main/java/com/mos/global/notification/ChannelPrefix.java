package com.mos.global.notification;

import lombok.Getter;

@Getter
public enum ChannelPrefix {
  COMMENT_CHANNEL_PREFIX("comment:noti:member:");

  private final String prefix;

  ChannelPrefix(String prefix) {
    this.prefix = prefix;
  }

}
