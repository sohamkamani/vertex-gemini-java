package com.sohamkamani;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;

/**
 * This is a simple example of how to use the Vertex AI Generative AI API to generate content.
 */
public class App {
	public static void main(String[] args) throws IOException {
		try (VertexAI vertexAi = new VertexAI("sohamkamani-demo", "us-central1");) {
			GenerationConfig generationConfig =
					GenerationConfig.newBuilder()
							.setMaxOutputTokens(8192)
							.setTemperature(1F)
							.setTopP(0.95F)
							.build();
			List<SafetySetting> safetySettings = Arrays.asList(
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
							.build(),
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
							.build(),
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
							.build(),
					SafetySetting.newBuilder()
							.setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
							.setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
							.build());
			GenerativeModel model =
					new GenerativeModel.Builder()
							.setModelName("gemini-1.5-flash-001")
							.setVertexAi(vertexAi)
							.setGenerationConfig(generationConfig)
							.setSafetySettings(safetySettings)
							.build();

			var text1 =
					"Write a short story about a robot who learns to love the smell of freshly baked bread. Make sure it's funny and heartwarming.";

			var content = ContentMaker.fromMultiModalData(text1);
			GenerateContentResponse response =
					model.generateContent(content);

			System.out.println("Response:" + response);
			// Do something with the response
			// responseStream.stream().forEach(System.out::println);
		}


	}
}
