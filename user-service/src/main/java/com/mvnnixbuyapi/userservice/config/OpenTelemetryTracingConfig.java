package com.mvnnixbuyapi.userservice.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class OpenTelemetryTracingConfig {
//    private final OtlpGrpcSpanExporter otlpGrpcSpanExporter;
//
//    public OpenTelemetryTracingConfig(OtlpGrpcSpanExporter otlpGrpcSpanExporter) {
//        this.otlpGrpcSpanExporter = otlpGrpcSpanExporter;
//    }
//
//    @Bean
//    public OpenTelemetry openTelemetry() {
//        // Configura el BatchSpanProcessor con el exportador
//        BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(otlpGrpcSpanExporter).build();
//
//        // Configura el TracerProvider
//        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
//                .addSpanProcessor(spanProcessor)
//                .build();
//
//        // Crea y devuelve la instancia de OpenTelemetry
//        return OpenTelemetrySdk.builder()
//                .setTracerProvider(tracerProvider)
//                .build();
//    }
//
//    @Bean
//    public Tracer tracer(OpenTelemetry openTelemetry) {
//        // Devuelve el tracer para instrumentar manualmente si es necesario
//        return openTelemetry.getTracer("user-service");
//    }

}
