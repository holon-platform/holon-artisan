import { css, html, LitElement } from 'lit';

class HolonInputFilter extends LitElement {
	
	static get styles() {
		return css`

		   	:host {
    			display: flex;
    			display: -webkit-box;
    			display: -webkit-flex;
    			display: -moz-box;
    			display: -ms-flexbox;
				flex-direction: row;
				-webkit-flex-direction: row;
				-moz-flex-direction: row;
				-ms-flex-direction: row;
				-o-flex-direction: row;
    			justify-content: center;
    			align-items: center;
    		}
    		
    		[part="operator"] {
    			flex-grow: 0;
    			flex-shrink: 0;
    			margin-right: 1px;
    		}
    		
    		[part="input-container"] {
    			display: block;
    			flex-grow: 1;
    		}
    		
    		[part="input"] {
    			float: left;
    			width: 100%;
    		}
    		
    		[part="additional-input"] {
    			display: none;
    		}
    		
    		:host([with-additional-input]) [part="input"] {
    			width: 50%;
    		}
    		
    		:host([with-additional-input]) [part="additional-input"] {
    			display: block;
    			width: calc(50% - 1px);
    			float: right;
    		}
	
    `;
	}
	
	render() {
		return html`
    	<div part="operator">
    		<slot name="operator"></slot>
    	</div>
    	<div part="input-container">
    		<div part="input">
    			<slot name="input"></slot>
    		</div>
    		<div part="additional-input">
    			<slot name="additional-input"></slot>
    		</div>
    	</div>`;
	}

}

customElements.define('holon-input-filter', HolonInputFilter);