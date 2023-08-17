package telran.view;

import java.time.LocalDate;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	public String readString(String prompt);

	public void write(Object string);

	default public void writeLine(Object obj) {
		write(obj + "\n");
	}

	default public <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
		boolean running = false;
		T res = null;
		do {
			running = false;
			String resInput = readString(prompt);
			try {
				res = mapper.apply(resInput);

			} catch (Exception e) {
				writeLine(errorPrompt + ": " + e.getMessage());
				running = true;
			}

		} while (running);

		return res;
	}

	default public int readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	default public int readInt(String prompt, String errorPrompt, int min, int max) {
		return readObject(String.format("%s[%d - %d]", prompt, min, max), errorPrompt, string -> {
			int res = Integer.parseInt(string);
			if (res < min) {
				throw new IllegalArgumentException(" must be not less than " + min);
			}
			if (res > max) {
				throw new IllegalArgumentException(" must be not greater than " + max);
			}
			return res;
		});

	}

	default public long readLong(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Long::parseLong);
	}

	default public long readLong(String prompt, String errorPrompt, long min, long max) {

		return readObject(prompt, errorPrompt, sl -> {
			Long res = Long.parseLong(sl);
			if (res < min) {
				throw new IllegalArgumentException(" must be not less than " + min);
			}
			if (res > max) {
				throw new IllegalArgumentException(" must be not greater than " + max);
			}
			return res;
		});
	}

	default public String readString(String prompt, String errorPrompt, Predicate<String> pred) {

		return readObject(prompt, errorPrompt, s -> {
			String res = s;
			if (!pred.test(s)) {
				throw new IllegalArgumentException(s + " don`t mutch ");
			}
			return res;
		});
	}

	default public String readString(String prompt, String errorPrompt, Set<String> options) {

		return readObject(prompt, errorPrompt, s -> {
			String res = s;
			if (!options.contains(s)) {
				throw new IllegalArgumentException(s + " -> isn`t mutch ");
			}
			;
			return res;
		});
	}

	default public LocalDate readDate(String prompt, String errorPrompt) {

		return readObject(prompt, errorPrompt, LocalDate::parse);
	}

	default public LocalDate readDate(String prompt, String errorPrompt, LocalDate from, LocalDate to) {

		return readObject(prompt, errorPrompt, sld -> {
			LocalDate res = LocalDate.parse(sld);
			if (!res.isAfter(from)) {
				throw new IllegalArgumentException(" must be not less than " + from);
			}
			if (!res.isBefore(to)) {
				throw new IllegalArgumentException(" must be not greater than " + to);
			}
			return res;
		});
	}

	default public double readDouble(String prompt, String errorPrompt) {

		return readObject(prompt, errorPrompt, Double::parseDouble);
	}
}
