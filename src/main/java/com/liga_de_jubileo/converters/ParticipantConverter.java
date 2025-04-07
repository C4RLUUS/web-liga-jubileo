package com.liga_de_jubileo.converters;

import org.springframework.stereotype.Component;

import com.liga_de_jubileo.dtos.ParticipantDto;
import com.liga_de_jubileo.entities.Participant;
import com.liga_de_jubileo.externalDto.ParticipantsExternalDto;

@Component("ParticipantConverter")
public class ParticipantConverter extends AbstractConverter<Participant, ParticipantDto, ParticipantsExternalDto>{


}
