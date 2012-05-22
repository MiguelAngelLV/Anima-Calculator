package cc.utilidades;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public class EditTextEval extends EditText implements TextWatcher  {

	private int actual;
	private OnChangeEval onchange;
	
	
	public EditTextEval(Context context, AttributeSet attrs) {
		super(context, attrs);
		setText("0");
		addTextChangedListener(this);
		setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
		setKeyListener(DigitsKeyListener.getInstance("0123456789+-"));

	   
	}
	
	
	public void setOnChangeEval(OnChangeEval onchange) {
		this.onchange = onchange;
	}
	

	
	public interface OnChangeEval {
		void onChangeValue(View v, int value);
		
	}


	@Override
	public void afterTextChanged(Editable s) {
		int valor = Eval.string(s.toString());
		if (valor != actual && onchange != null)
			onchange.onChangeValue(this, valor);
		
		actual = valor;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		
	};


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	
}
