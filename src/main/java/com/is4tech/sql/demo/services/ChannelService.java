package com.is4tech.sql.demo.services;

import com.is4tech.sql.demo.models.Channels;
import com.is4tech.sql.demo.repository.IChannelRepository;
import com.is4tech.sql.demo.repository.IUserRepository;
import com.is4tech.sql.demo.services.dto.IChannelDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService implements IChannelDTO {
  private final IChannelRepository channelRepo;
  private final IUserRepository userRepo;

  public ChannelService(IChannelRepository channelRepo, IUserRepository userRepo) {
    this.channelRepo = channelRepo;
    this.userRepo = userRepo;
  }

  @Override
  public Channels createChanel(Channels channel, Long user_id) {
    var usr = this.userRepo.findById(user_id);
    if (usr.isEmpty()) {
      return null;
    }
    channel.setUser(usr.get());
    return this.channelRepo.save(channel);
  }

  @Override
  public Optional<Channels> getChannelByID(Long id) {
    return this.channelRepo.findById(id);
  }

  @Override
  public List<Channels> getALL() {
    return this.channelRepo.findAll();
  }

  @Override
  public Channels updateChannelById(Long id, Channels channel) {
    var channel_selected = this.channelRepo.findById(id);
    if (channel_selected.isEmpty()) {
      return null;
    }
    channel_selected.get().setName(channel.getName());
    return this.channelRepo.save(channel_selected.get());
  }

  @Override
  public void deleteChannelById(Long id) {
    this.channelRepo.deleteById(id);
  }
}
