package org.example.functions.transformer;

import org.example.functions.dto.LoadingDataDto;
import org.example.functions.dto.SourceDataDto;

public interface SourceDataTransformer {
	LoadingDataDto transformData(SourceDataDto sourceDataDto);
}
