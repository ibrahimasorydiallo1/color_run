// Gestionnaire d'images centralisé
class ImageHandler {
    constructor(options = {}) {
        this.maxSize = options.maxSize || 5 * 1024 * 1024; // 5MB par défaut
        this.acceptedTypes = options.acceptedTypes || ['image/jpeg', 'image/png', 'image/gif'];
        this.previewElement = options.previewElement;
        this.inputElement = options.inputElement;
        this.dropZone = options.dropZone;
        
        this.init();
    }
    
    init() {
        if (this.inputElement) {
            this.inputElement.addEventListener('change', (e) => this.handleFileSelect(e));
        }
        
        if (this.dropZone) {
            this.initDragAndDrop();
        }
    }
    
    initDragAndDrop() {
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            this.dropZone.addEventListener(eventName, (e) => {
                e.preventDefault();
                e.stopPropagation();
            });
        });
        
        ['dragenter', 'dragover'].forEach(eventName => {
            this.dropZone.addEventListener(eventName, () => {
                this.dropZone.classList.add('border-purple-500', 'bg-purple-50');
            });
        });
        
        ['dragleave', 'drop'].forEach(eventName => {
            this.dropZone.addEventListener(eventName, () => {
                this.dropZone.classList.remove('border-purple-500', 'bg-purple-50');
            });
        });
        
        this.dropZone.addEventListener('drop', (e) => {
            const files = e.dataTransfer.files;
            if (files.length > 0) {
                this.handleFileSelect({ target: { files } });
            }
        });
    }
    
    handleFileSelect(event) {
        const file = event.target.files[0];
        if (!file) return;
        
        // Vérifier le type de fichier
        if (!this.acceptedTypes.includes(file.type)) {
            this.showError('Format de fichier non supporté. Veuillez utiliser JPG, PNG ou GIF.');
            return;
        }
        
        // Vérifier la taille du fichier
        if (file.size > this.maxSize) {
            this.showError(`L'image ne doit pas dépasser ${this.maxSize / (1024 * 1024)}MB`);
            return;
        }
        
        // Afficher l'aperçu
        const reader = new FileReader();
        reader.onload = (e) => {
            if (this.previewElement) {
                if (this.previewElement.tagName === 'IMG') {
                    this.previewElement.src = e.target.result;
                } else {
                    this.previewElement.style.backgroundImage = `url(${e.target.result})`;
                }
            }
        };
        reader.readAsDataURL(file);
        
        // Retourner le fichier pour traitement ultérieur
        return file;
    }
    
    showError(message) {
        // Créer une notification d'erreur
        const notification = document.createElement('div');
        notification.className = 'fixed top-4 right-4 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded z-50';
        notification.innerHTML = `
            <div class="flex items-center">
                <i class="fas fa-exclamation-circle mr-2"></i>
                <span>${message}</span>
            </div>
        `;
        
        document.body.appendChild(notification);
        
        // Supprimer la notification après 3 secondes
        setTimeout(() => {
            notification.classList.add('opacity-0');
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    }
    
    // Convertir une image en base64
    static async fileToBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });
    }
}

// Exporter la classe
window.ImageHandler = ImageHandler; 