package org.toadallyarmed.util.action;

public abstract class ActionAdapter<Out, In> implements Action<In> {
    private final Action<Out> action;
    private final PayloadExtractor<Out, In> extractor;

    public ActionAdapter(
        Action<Out> action,
        PayloadExtractor<Out, In> extractor
    ) {
        this.action = action;
        this.extractor = extractor;
    }

    @Override
    public void run(In payload) {
        extractor.extract(payload).ifPresent(action::run);
    }
}
