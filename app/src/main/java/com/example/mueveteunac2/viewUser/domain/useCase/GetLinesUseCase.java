package com.example.mueveteunac2.viewUser.domain.useCase;

import com.example.mueveteunac2.viewUser.data.model.Line;

import java.util.List;

public interface GetLinesUseCase {
        void linesAdded(List<Line> lineList);
}
