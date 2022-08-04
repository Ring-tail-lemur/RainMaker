package com.ringtaillemur.rainmaker.controller.maindashboard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.MainCycleTimeResponseDto;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class mainController {


	@ResponseBody
	@GetMapping("/api/cycletime")
	public MainCycleTimeResponseDto mainDashboardCycleTimeApi() throws InterruptedException {
//		cycleTimeService.test();
		return new MainCycleTimeResponseDto();
	}
}
