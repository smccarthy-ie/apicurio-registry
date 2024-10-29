package io.apicurio.registry.operator.utils;

import io.apicurio.registry.operator.OperatorException;
import io.apicurio.registry.operator.api.v1.ApicurioRegistry3;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.networking.v1.HTTPIngressPath;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressRule;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static io.apicurio.registry.operator.resource.ResourceFactory.COMPONENT_APP;
import static io.apicurio.registry.operator.resource.ResourceFactory.COMPONENT_UI;
import static io.apicurio.registry.operator.utils.Utils.isBlank;

public class IngressUtils {

    private static final Logger log = LoggerFactory.getLogger(IngressUtils.class);

    private IngressUtils() {
    }

    public static String getHost(String component, ApicurioRegistry3 p) {
        String host = null;
        if (COMPONENT_APP.equals(component)) {
            if (!isBlank(p.getSpec().getApp().getHost())) {
                host = p.getSpec().getApp().getHost();
            }
        } else if (COMPONENT_UI.equals(component)) {
            if (!isBlank(p.getSpec().getUi().getHost())) {
                host = p.getSpec().getUi().getHost();
            }
        } else {
            throw new OperatorException("Unexpected value: " + component);
        }
        if (host == null) {
            var defaultBaseHost = ConfigProvider.getConfig()
                    .getOptionalValue("apicurio.operator.default-base-host", String.class).map(v -> "." + v)
                    .orElse("");
            host = "%s-%s.%s%s".formatted(p.getMetadata().getName(), component,
                    p.getMetadata().getNamespace(), defaultBaseHost);
        }
        log.debug("Host for component {} is {}", component, host);
        return host;
    }

    public static void withIngressRule(Service s, Ingress i, Consumer<IngressRule> action) {
        for (IngressRule rule : i.getSpec().getRules()) {
            for (HTTPIngressPath path : rule.getHttp().getPaths()) {
                if (s.getMetadata().getName().equals(path.getBackend().getService().getName())) {
                    action.accept(rule);
                    return;
                }
            }
        }
    }
}