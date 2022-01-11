package com.is4tech.sql.demo;

import com.is4tech.sql.demo.models.Channels;
import com.is4tech.sql.demo.services.dto.IChannelDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ChannelServiceTest {
  @MockBean
  private IChannelDTO channelDTO;

  @Test
  void createChannel() {
    var channel = new Channels();
    channel.setChannel_id(1L);
    channel.setName("discord");

    Mockito.when(this.channelDTO.createChanel(channel, 1L)).thenReturn(channel);

    var channel_created = this.channelDTO.createChanel(channel, 1L);

    Assertions.assertEquals(channel_created.getChannel_id(), channel.getChannel_id());
    Assertions.assertEquals(channel_created.getName(), channel.getName());
  }

  @Test
  void createChannelFailed() {
    var channel = new Channels();
    channel.setChannel_id(1L);
    channel.setName("discord");

    Mockito.when(this.channelDTO.createChanel(channel, 1L)).thenReturn(null);

    var channel_created = this.channelDTO.createChanel(channel, 1L);

    Assertions.assertEquals(channel_created, null);
  }

  @Test
  void getChannel() {
    var channel = new Channels();
    channel.setChannel_id(1L);
    channel.setName("discord");

    Mockito.when(this.channelDTO.getChannelByID(1L)).thenReturn(Optional.of(channel));
    var channel_selected = this.channelDTO.getChannelByID(1L);

    Assertions.assertEquals(channel.getName(), channel_selected.get().getName());
    Assertions.assertEquals(channel.getChannel_id(), channel_selected.get().getChannel_id());
  }

  @Test
  void getChannels() {
    var channel = new Channels();
    channel.setChannel_id(1L);
    channel.setName("discord");

    var channels = new ArrayList<Channels>();
    channels.add(channel);

    Mockito.when(this.channelDTO.getALL()).thenReturn(channels);

    var channels_selected = this.channelDTO.getALL();
    Assertions.assertEquals(1, channels_selected.size());
  }

  @Test
  void updateChannel() {
    var channel = new Channels();
    channel.setChannel_id(1L);
    channel.setName("discord");

    Mockito.when(this.channelDTO.updateChannelById(1L, channel)).thenReturn(channel);
    var channel_selected = this.channelDTO.updateChannelById(1L, channel);
    Assertions.assertEquals(channel.getName(), channel_selected.getName());
    Assertions.assertEquals(channel.getChannel_id(), channel_selected.getChannel_id());
  }

  @Test
  void updateChannelFailed() {
    var channel = new Channels();
    channel.setChannel_id(1L);
    channel.setName("discord");

    Mockito.when(this.channelDTO.updateChannelById(1L, channel)).thenReturn(null);
    var channel_selected = this.channelDTO.updateChannelById(1L, channel);
    Assertions.assertEquals(null, channel_selected);
  }
}
