package cc.utilidades;


import java.util.Arrays;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import cc.anima.ui.R;


public class DialogMultiChoiceList extends ListView implements DialogMultiChoice {

	private Campo campos[];
	private OnMultiChoiceChange onchange;
	
	
	public DialogMultiChoiceList(Context context, AttributeSet attrs) {
		super(context, attrs);
			
			
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DialogMultiChoice);
		
		CharSequence nombres[] = a.getTextArray(R.styleable.DialogMultiChoice_android_entries);
		CharSequence valores[] = a.getTextArray(R.styleable.DialogMultiChoice_android_entryValues);
		
		if (nombres == null)
			nombres = new CharSequence[0];
		
		if (valores == null) {
			valores = new CharSequence[nombres.length];
			Arrays.fill(valores, "0");
		} 
		
		campos = new Campo[nombres.length];
		
		for (int i = 0; i < nombres.length; i++) {
			campos[i] = new Campo();
			campos[i].nombre = nombres[i].toString();
			campos[i].valor = Integer.parseInt(valores[i].toString());		
		}
		
		Arrays.sort(campos);
		

		a.recycle();
		
		setAdapter(adapter);
		setCacheColorHint(Color.TRANSPARENT);
	}


	
	private BaseAdapter adapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			CheckBox cb = (CheckBox) convertView;

			if (cb == null) {
				cb = new CheckBox(getContext());
				cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						int posicion = (Integer) buttonView.getTag();
						if (campos[posicion].estado == isChecked) return;
						campos[posicion].estado = isChecked;
						if (onchange != null) onchange.onMultiChoiceChange(DialogMultiChoiceList.this, posicion, campos[posicion].estado, campos[posicion].valor);
					}
				});
			}
			
			cb.setTag(position);
			cb.setText(campos[position].nombre);
			cb.setChecked(campos[position].estado);
			return cb;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return campos[position].nombre;
		}
		
		@Override
		public int getCount() {
			return campos.length;
		}
	};
	
	private static class Campo implements Comparable<Campo>{
		int valor;
		String nombre;
		boolean estado;
		
		@Override
		public int compareTo(Campo a) {
			return nombre.compareTo(a.nombre);
		}
		
	}

	@Override
	public void setOnMultiChoceChange(OnMultiChoiceChange onchange) {
		this.onchange = onchange;
	}

	
}
