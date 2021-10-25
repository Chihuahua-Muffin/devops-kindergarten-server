package devops.kindergarten.server.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShUtil {
	public final String pwd;
	public final String scriptName;

	public ShUtil(
		@Value("${shell.pwd}") String pwd,
		@Value("${shell.script-name}") String name) {
		this.pwd = pwd;
		this.scriptName = name;
	}

	@Async
	public void executeCommand(Long userId) {
		String cmd = String.format("%s/%s %s", pwd, scriptName, userId.toString());
		String[] callCmd = {"/bin/bash", "-c", cmd};
		ProcessBuilder pb = new ProcessBuilder(callCmd);
		pb.redirectErrorStream(true);
		Process process;
		try {
			process = pb.start();
			log.info(Long.toString(process.pid()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
