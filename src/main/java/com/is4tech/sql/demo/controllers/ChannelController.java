package com.is4tech.sql.demo.controllers;

import com.is4tech.sql.demo.models.Channels;
import com.is4tech.sql.demo.services.dto.IChannelDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
  private final IChannelDTO channelDTO;

  public ChannelController(IChannelDTO channelDTO) {
    this.channelDTO = channelDTO;
  }

  @PostMapping("/")
  private Channels createChannel(@RequestBody Channels channel, @RequestParam Long user_id) {
    return channelDTO.createChanel(channel, user_id);
  }

  @GetMapping("/")
  private List<Channels> getChannels() {
    return this.channelDTO.getALL();
  }
  @GetMapping("/{id}")
  private Optional<Channels> getChannel(@PathVariable Long id) {
    return this.channelDTO.getChannelByID(id);
  }

  @PutMapping("/{id}")
  private Channels updateChannel(@PathVariable Long id, @RequestBody Channels channel) {
    return this.channelDTO.updateChannelById(id, channel);
  }

  @DeleteMapping("/{id}")
  private void deleteChannel(@PathVariable Long id) {
    this.channelDTO.deleteChannelById(id);
  }
}
