package com.eror.service.impl;

import com.eror.dto.LjubimacDTO;
import com.eror.entity.Ljubimac;
import com.eror.entity.LjubimacTip;
import com.eror.entity.Vlasnik;
import com.eror.exception.EntityNotFoundException;
import com.eror.mapper.LjubimacMapper;
import com.eror.repository.LjubimacRepository;
import com.eror.repository.LjubimacTipRepository;
import com.eror.repository.VlasnikRepository;
import com.eror.service.LjubimacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class LjubimacServiceImp implements LjubimacService {
    private final LjubimacMapper ljubimacMapper;
    private final LjubimacRepository ljubimacRepository;
    private final VlasnikRepository vlasnikRepository;
    private final LjubimacTipRepository ljubimacTIpRepository;

    @Autowired
    public LjubimacServiceImp(LjubimacRepository ljubimacRepository, LjubimacMapper ljubimacMapper, VlasnikRepository vlasnikRepository, LjubimacTipRepository ljubimacTIpRepository) {
        super();
        this.ljubimacRepository = ljubimacRepository;
        this.vlasnikRepository = vlasnikRepository;
        this.ljubimacMapper = ljubimacMapper;
        this.ljubimacTIpRepository = ljubimacTIpRepository;
    }

    public List<Ljubimac> listaLjubimaca() {
        List<Ljubimac> listaLjubimaca = ljubimacRepository.findAll();
        return listaLjubimaca;
    }

    @Override
    @Transactional
    public LjubimacDTO save(Ljubimac ljubimac) {
        ljubimac = ljubimacRepository.save(ljubimac);
        LjubimacDTO ljubimacDto = ljubimacMapper.toLjubimacDto(ljubimac);
        return ljubimacDto;
    }

    @Override
    @Transactional
    public void deleteAll(List<LjubimacDTO> lista) {
        List<Ljubimac> listaLjubimaca = ljubimacMapper.toLjubimac(lista);
        List<Ljubimac> listaLjubimacaTemp = listaLjubimaca;
        for (Ljubimac lj : listaLjubimacaTemp) {
            ljubimacRepository.delete(lj);
        }


    }

    @Override
    @Transactional
    public LjubimacDTO updateLjubimca(LjubimacDTO ljubimacDto, Integer idVlasnika, Integer idLjubimacTip) {
        Vlasnik vlasnik = vlasnikRepository.findVlasnikById(idVlasnika);
        if (vlasnik == null) throw new EntityNotFoundException("Ne postoji vlasnik");
        LjubimacTip ljubimacTip = ljubimacTIpRepository.findLjubimacTipById(idLjubimacTip);
        if (vlasnik == null) throw new EntityNotFoundException("Ne postoji ljubimac");
        Ljubimac ljubimac = ljubimacMapper.toLjubimac(ljubimacDto);
        try {
            ljubimac.setLjubimacTip(ljubimacTip);
            ljubimac.setVlasnik(vlasnik);
            ljubimacRepository.save(ljubimac);
        } catch (Exception e) {
            throw new EntityNotFoundException("Nije sacuvan podatak");
        }
        return ljubimacDto = ljubimacMapper.toLjubimacDto(ljubimac);
    }


}
