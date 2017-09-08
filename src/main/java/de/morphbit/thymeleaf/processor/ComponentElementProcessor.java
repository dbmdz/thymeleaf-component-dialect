package de.morphbit.thymeleaf.processor;

import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import de.morphbit.thymeleaf.helper.FragmentHelper;

public class ComponentElementProcessor
        extends AbstractDefaultElementModelProcessor {

	private static final String TAG_NAME = "component";
	private static final int PRECEDENCE = 75;

	private static final String THYMELEAF_FRAGMENT_PREFIX = "th";
	private static final String THYMELEAF_FRAGMENT_ATTRIBUTE = "fragment";

	private static final String REPLACE_CONTENT_TAG = "tc:content";

	public ComponentElementProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false,
		    PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IModel model,
	        IElementModelStructureHandler structureHandler) {

		IProcessableElementTag tag = processElementTag(context, model);
		Map<String, String> attrMap = processAttribute(context, tag);

		IModel base = model.cloneModel();
		base.remove(0);
		base.remove(base.size() - 1);

		IModel frag = FragmentHelper.getFragmentModel(context,
		    attrMap.get("name"), structureHandler, THYMELEAF_FRAGMENT_PREFIX,
		    THYMELEAF_FRAGMENT_ATTRIBUTE);

		model.reset();
		model.addModel(mergeModel(frag, base, REPLACE_CONTENT_TAG));

	}

	

}
